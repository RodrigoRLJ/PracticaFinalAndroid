package com.example.tiendafragments.ui.fregment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendafragments.databinding.ItemSerieBinding
import com.example.tiendafragments.model.Serie

class SeriesAdapter(private val onItemClick: (Serie) -> Unit) :
    RecyclerView.Adapter<SeriesAdapter.SerieViewHolder>() {

    private var lista: List<Serie> = emptyList()

    fun changeList(nuevaLista: List<Serie>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val binding = ItemSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SerieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = lista[position]
        holder.binding.txtNombreSerie.text = serie.name
        Glide.with(holder.itemView.context)
            .load(serie.image?.medium)
            .into(holder.binding.imgSerie)
        holder.itemView.setOnClickListener { onItemClick(serie) }
    }

    override fun getItemCount() = lista.size

    inner class SerieViewHolder(val binding: ItemSerieBinding) :
        RecyclerView.ViewHolder(binding.root)
}
