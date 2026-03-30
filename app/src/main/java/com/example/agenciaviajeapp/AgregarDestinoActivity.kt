package com.example.agenciaviajeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AgregarDestinoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var spPais: Spinner
    private lateinit var etPrecio: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnGuardar: Button

    private var imageUri: Uri? = null

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // 🔥 NUEVO MÉTODO para seleccionar imagen (sin deprecated)
    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data
            Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_destino)

        etNombre = findViewById(R.id.etNombre)
        spPais = findViewById(R.id.spPais)
        etPrecio = findViewById(R.id.etPrecio)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnGuardar = findViewById(R.id.btnGuardar)

        // 📌 Spinner de países
        val paises = arrayOf("El Salvador", "México", "España", "Estados Unidos")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPais.adapter = adapter

        // 📸 Seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePicker.launch(intent)
        }

        // 💾 Guardar destino
        btnGuardar.setOnClickListener {
            guardarDestino()
        }
    }

    private fun guardarDestino() {
        val nombre = etNombre.text.toString().trim()
        val pais = spPais.selectedItem.toString()
        val precioStr = etPrecio.text.toString().trim()
        val descripcion = etDescripcion.text.toString().trim()

        // ✅ VALIDACIONES
        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull()
        if (precio == null || precio <= 0) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
            return
        }

        if (descripcion.length < 20) {
            Toast.makeText(this, "Descripción mínima de 20 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // 🔥 SUBIR IMAGEN A FIREBASE STORAGE
        val fileName = UUID.randomUUID().toString()
        val ref = storage.reference.child("destinos/$fileName")

        ref.putFile(imageUri!!)
            .addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener { uri: Uri ->

                    // 📦 Crear objeto destino
                    val destino = hashMapOf(
                        "nombre" to nombre,
                        "pais" to pais,
                        "precio" to precio,
                        "descripcion" to descripcion,
                        "imagenUrl" to uri.toString()
                    )

                    // 🔥 Guardar en Firestore
                    db.collection("destinos")
                        .add(destino)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Destino guardado", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al subir imagen", Toast.LENGTH_SHORT).show()
            }
    }
}