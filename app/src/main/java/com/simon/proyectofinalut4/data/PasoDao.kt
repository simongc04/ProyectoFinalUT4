package com.simon.proyectofinalut4.data

import androidx.room.*

@Dao
interface PasoDao {

    @Insert
    suspend fun insert(paso: Paso)

    @Update
    suspend fun update(paso: Paso)

    @Delete
    suspend fun delete(paso: Paso)

    @Query("SELECT * FROM pasos WHERE recetaId = :recetaId")
    suspend fun getByRecetaId(recetaId: Long): List<Paso>
}
