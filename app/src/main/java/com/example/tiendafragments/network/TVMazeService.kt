package com.example.tiendafragments.network

import com.example.tiendafragments.model.Serie
import retrofit2.Call
import retrofit2.http.GET

interface TVMazeService {
    @GET("shows")
    fun getShows(): Call<List<Serie>>
}
