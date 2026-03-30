package com.example.agenciaviajeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var listaDestinos: MutableList<Destino>
    private lateinit var adapter: DestinoAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recyclerDestinos)
        btnAgregar = findViewById(R.id.btnAgregar)

        listaDestinos = mutableListOf()
        adapter = DestinoAdapter(listaDestinos)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        db = FirebaseFirestore.getInstance()

        // 🔥 CARGAR DATOS DE FIRESTORE
        cargarDestinos()

        // BOTÓN PARA IR A AGREGAR DESTINO
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarDestinoActivity::class.java))
        }
    }

    private fun cargarDestinos() {
        db.collection("destinos")
            .get()
            .addOnSuccessListener { resultado ->
                listaDestinos.clear()

                for (documento in resultado) {
                    val destino = documento.toObject(Destino::class.java)
                    listaDestinos.add(destino)
                }

                adapter.notifyDataSetChanged()
            }
    }
}