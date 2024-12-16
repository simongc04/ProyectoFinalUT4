package com.simon.proyectofinalut4.data



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



