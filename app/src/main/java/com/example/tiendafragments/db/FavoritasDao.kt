package com.example.tiendafragments.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiendafragments.model.SeriesFavorita

@Dao
interface FavoritasDao {
    @Query("SELECT * FROM series_favoritas")
    fun getAll(): List<SeriesFavorita>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(serie: SeriesFavorita)

    @Delete
    fun delete(serie: SeriesFavorita)

    @Query("SELECT * FROM series_favoritas WHERE id = :id LIMIT 1")
    fun getById(id: Int): SeriesFavorita?
}
