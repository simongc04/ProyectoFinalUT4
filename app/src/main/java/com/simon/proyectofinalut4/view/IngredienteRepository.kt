package com.simon.proyectofinalut4.view

import android.content.Context
import com.simon.proyectofinalut4.data.AppDatabase
import com.simon.proyectofinalut4.data.Ingrediente
import com.simon.proyectofinalut4.data.IngredienteDao

class IngredienteRepository(context: Context) {
    private val ingredienteDao: IngredienteDao = AppDatabase.getDatabase(context).ingredienteDao()

    suspend fun getIngredientesByRecetaId(recetaId: Long): List<Ingrediente> {
        return ingredienteDao.getByRecetaId(recetaId)
    }

    suspend fun addIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.insert(ingrediente)
    }

    suspend fun updateIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.update(ingrediente)
    }

    suspend fun deleteIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.delete(ingrediente)
    }
}
