package com.example.tiendafragments.ui.fregment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendafragments.R
import com.example.tiendafragments.databinding.FragmentSeriesListBinding
import com.example.tiendafragments.model.Serie
import com.example.tiendafragments.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.ceil

class SeriesListFragment : Fragment() {

    private lateinit var binding: FragmentSeriesListBinding
    private lateinit var adapter: SeriesAdapter

    private var todasLasSeries: List<Serie> = emptyList()
    private var currentPage = 0
    private val PAGE_SIZE = 9
    private val TOTAL_SHOWS = 90

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SeriesAdapter { serie ->
            val bundle = Bundle().apply { putParcelable("serie", serie) }
            findNavController().navigate(R.id.action_seriesListFragment_to_seriesDetailFragment, bundle)
        }

        binding.recyclerSeries.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerSeries.adapter = adapter

        binding.btnAnterior.setOnClickListener { mostrarPagina(currentPage - 1) }
        binding.btnSiguiente.setOnClickListener { mostrarPagina(currentPage + 1) }

        loadSeries()
    }

    private fun loadSeries() {
        RetrofitClient.instance.getShows().enqueue(object : Callback<List<Serie>> {
            override fun onResponse(call: Call<List<Serie>>, response: Response<List<Serie>>) {
                if (response.isSuccessful) {
                    todasLasSeries = response.body()?.take(TOTAL_SHOWS) ?: emptyList()
                    mostrarPagina(0)
                }
            }

            override fun onFailure(call: Call<List<Serie>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar series", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarPagina(pagina: Int) {
        currentPage = pagina
        val desde = pagina * PAGE_SIZE
        val hasta = minOf(desde + PAGE_SIZE, todasLasSeries.size)
        adapter.changeList(todasLasSeries.subList(desde, hasta))

        val totalPaginas = ceil(todasLasSeries.size / PAGE_SIZE.toDouble()).toInt()
        binding.txtPagina.text = "Página ${pagina + 1} / $totalPaginas"
        binding.btnAnterior.isEnabled = pagina > 0
        binding.btnSiguiente.isEnabled = pagina < totalPaginas - 1
    }

    override fun onDetach() {
        super.onDetach()
    }
}
