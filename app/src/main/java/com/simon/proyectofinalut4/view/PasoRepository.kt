package com.simon.proyectofinalut4.view

import android.content.Context
import com.simon.proyectofinalut4.data.AppDatabase
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.PasoDao

class PasoRepository(context: Context) {
    private val pasoDao: PasoDao = AppDatabase.getDatabase(context).pasoDao()

    suspend fun getPasosByRecetaId(recetaId: Long): List<Paso> {
        return pasoDao.getByRecetaId(recetaId)
    }

    suspend fun addPaso(paso: Paso) {
        pasoDao.insert(paso)
    }

    suspend fun updatePaso(paso: Paso) {
        pasoDao.update(paso)
    }

    suspend fun deletePaso(paso: Paso) {
        pasoDao.delete(paso)
    }
}
