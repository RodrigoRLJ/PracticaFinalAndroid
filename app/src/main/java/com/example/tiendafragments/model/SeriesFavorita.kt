package com.example.tiendafragments.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series_favoritas")
data class SeriesFavorita(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "resumen") val resumen: String?,
    @ColumnInfo(name = "imagen") val imagen: String?,
    @ColumnInfo(name = "valoracion") val valoracion: Double?
)
