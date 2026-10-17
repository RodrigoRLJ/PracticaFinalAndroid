package com.example.tiendafragments.ui.fregment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendafragments.databinding.ItemFavoritaBinding
import com.example.tiendafragments.model.SeriesFavorita

class FavoritasAdapter(private val onDeleteClick: (SeriesFavorita) -> Unit) :
    RecyclerView.Adapter<FavoritasAdapter.FavoritaViewHolder>() {

    private var lista: List<SeriesFavorita> = emptyList()

    fun changeList(nuevaLista: List<SeriesFavorita>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritaViewHolder {
        val binding = ItemFavoritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritaViewHolder, position: Int) {
        val favorita = lista[position]
        holder.binding.txtNombreFavorita.text = favorita.nombre
        Glide.with(holder.itemView.context)
            .load(favorita.imagen)
            .into(holder.binding.imgFavorita)
        holder.binding.btnEliminar.setOnClickListener { onDeleteClick(favorita) }
    }

    override fun getItemCount() = lista.size

    inner class FavoritaViewHolder(val binding: ItemFavoritaBinding) :
        RecyclerView.ViewHolder(binding.root)
}
