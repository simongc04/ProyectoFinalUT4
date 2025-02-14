package com.simon.proyectofinalut4.data

import androidx.room.*

@Dao
interface PasoDao {

    @Insert
    suspend fun insertPaso(paso: Paso)

    @Update
    suspend fun updatePaso(paso: Paso)

    @Delete
    suspend fun deletePaso(paso: Paso)

    @Query("SELECT * FROM pasos WHERE recetaId = :recetaId")
    suspend fun getPasosByRecetaId(recetaId: Long): List<Paso>
}
