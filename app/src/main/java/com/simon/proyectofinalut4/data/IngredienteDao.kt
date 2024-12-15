package com.simon.proyectofinalut4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface IngredienteDao {

    @Insert
    suspend fun insert(ingrediente: Ingrediente)

    @Delete
    suspend fun delete(ingrediente: Ingrediente)

    @Query("SELECT * FROM ingredientes WHERE recetaId = :recetaId")
    suspend fun getByRecetaId(recetaId: Long): List<Ingrediente>
}
