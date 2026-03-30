package com.example.agenciaviajeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DestinoAdapter(private val lista: List<Destino>) :
    RecyclerView.Adapter<DestinoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val imgDestino: ImageView = itemView.findViewById(R.id.imgDestino)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destino, parent, false)

        return ViewHolder(view) // 🔥 IMPORTANTE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destino = lista[position]

        holder.tvNombre.text = destino.nombre
        holder.tvPrecio.text = "$${destino.precio}"
        holder.tvDescripcion.text = destino.descripcion

        Glide.with(holder.itemView.context)
            .load(destino.imagenUrl)
            .into(holder.imgDestino)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}