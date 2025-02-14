package com.simon.proyectofinalut4.data

import androidx.room.*

@Dao
interface IngredienteDao {

    @Insert
    suspend fun insertIngrediente(ingrediente: Ingrediente)

    @Update
    suspend fun updateIngrediente(ingrediente: Ingrediente)

    @Delete
    suspend fun deleteIngrediente(ingrediente: Ingrediente)

    @Query("SELECT * FROM ingredientes WHERE recetaId = :recetaId")
    suspend fun getIngredientesByRecetaId(recetaId: Long): List<Ingrediente>
}
