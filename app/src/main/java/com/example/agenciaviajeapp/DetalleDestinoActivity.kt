package com.example.agenciaviajeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetalleDestinoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)

        val tvNombre: TextView = findViewById(R.id.tvNombreDetalle)
        val tvPrecio: TextView = findViewById(R.id.tvPrecioDetalle)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcionDetalle)
        val imgDestino: ImageView = findViewById(R.id.imgDestinoDetalle)

        val destino = intent.getSerializableExtra("destino") as Destino

        tvNombre.text = destino.nombre
        tvPrecio.text = "$${destino.precio}"
        tvDescripcion.text = destino.descripcion

        Glide.with(this)
            .load(destino.imagenUrl)
            .into(imgDestino)
    }
}