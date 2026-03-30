package com.example.agenciaviajeapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DestinoAdapter(private val lista: List<Destino>) :
    RecyclerView.Adapter<DestinoAdapter.ViewHolder>() {

    // ViewHolder mantiene referencias a los elementos del layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val imgDestino: ImageView = itemView.findViewById(R.id.imgDestino)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destino, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destino = lista[position]

        holder.tvNombre.text = destino.nombre
        holder.tvPrecio.text = "$${destino.precio}"
        holder.tvDescripcion.text = destino.descripcion

        Glide.with(holder.itemView.context)
            .load(destino.imagenUrl)
            .into(holder.imgDestino)

        // Click en el item para abrir la Activity de detalle
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleDestinoActivity::class.java)
            intent.putExtra("destino", destino) // Destino debe implementar Serializable
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}