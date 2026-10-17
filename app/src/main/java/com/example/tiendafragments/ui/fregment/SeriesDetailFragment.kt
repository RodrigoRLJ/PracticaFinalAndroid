package com.example.tiendafragments.ui.fregment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tiendafragments.databinding.FragmentSeriesDetailBinding
import com.example.tiendafragments.db.AppDatabase
import com.example.tiendafragments.model.Serie
import com.example.tiendafragments.model.SeriesFavorita
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesDetailFragment : Fragment() {

    private lateinit var binding: FragmentSeriesDetailBinding
    private var serie: Serie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("serie", Serie::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("serie")
        }

        serie?.let { mostrarDetalle(it) }

        binding.btnAgregarFavorito.setOnClickListener { agregarFavorito() }
    }

    private fun mostrarDetalle(s: Serie) {
        binding.txtNombreDetalle.text = s.name
        binding.txtRatingDetalle.text = "Valoración: ${s.rating?.average ?: "N/A"}"
        binding.txtGenerosDetalle.text = "Géneros: ${s.genres.joinToString(", ").ifEmpty { "N/A" }}"
        binding.txtEstadoDetalle.text = "Estado: ${s.status ?: "N/A"}"
        binding.txtEstrenoDetalle.text = "Estreno: ${s.premiered ?: "N/A"}"

        val resumen = s.summary
        binding.txtResumenDetalle.text = if (!resumen.isNullOrEmpty()) {
            Html.fromHtml(resumen, Html.FROM_HTML_MODE_LEGACY)
        } else {
            "Sin resumen disponible."
        }

        Glide.with(this)
            .load(s.image?.original ?: s.image?.medium)
            .into(binding.imgDetalle)
    }

    private fun agregarFavorito() {
        val s = serie ?: return
        val favorita = SeriesFavorita(
            id = s.id,
            nombre = s.name,
            resumen = s.summary,
            imagen = s.image?.medium,
            valoracion = s.rating?.average
        )
        GlobalScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).favoritasDao().insert(favorita)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "${s.name} añadida a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
