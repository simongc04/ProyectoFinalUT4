package com.simon.proyectofinalut4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import androidx.room.*

@Dao
interface RecetaDao {

    @Insert
    suspend fun insertReceta(receta: Receta)

    @Update
    suspend fun updateReceta(receta: Receta)

    @Delete
    suspend fun deleteReceta(receta: Receta)

    @Query("SELECT * FROM recetas")
    suspend fun getAllRecetas(): List<Receta>
}

