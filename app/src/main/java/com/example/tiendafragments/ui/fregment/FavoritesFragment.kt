package com.example.tiendafragments.ui.fregment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendafragments.databinding.FragmentFavoritesBinding
import com.example.tiendafragments.db.AppDatabase
import com.example.tiendafragments.model.SeriesFavorita
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoritasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritasAdapter { favorita -> eliminarFavorita(favorita) }
        binding.recyclerFavoritas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavoritas.adapter = adapter

        loadFavoritas()
    }

    private fun loadFavoritas() {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = AppDatabase.getInstance(requireContext()).favoritasDao().getAll()
            withContext(Dispatchers.Main) {
                adapter.changeList(lista)
            }
        }
    }

    private fun eliminarFavorita(favorita: SeriesFavorita) {
        GlobalScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).favoritasDao().delete(favorita)
            withContext(Dispatchers.Main) {
                loadFavoritas()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
