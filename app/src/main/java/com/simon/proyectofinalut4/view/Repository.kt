package com.simon.proyectofinalut4.view

import android.content.Context
import com.simon.proyectofinalut4.data.AppDatabase
import com.simon.proyectofinalut4.data.Ingrediente
import com.simon.proyectofinalut4.data.IngredienteDao
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.PasoDao
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.data.RecetaDao

class Repository(context: Context) {
    private val recetaDao: RecetaDao = AppDatabase.getDatabase(context).recetaDao()
    private val pasoDao: PasoDao = AppDatabase.getDatabase(context).pasoDao()
    private val ingredienteDao: IngredienteDao = AppDatabase.getDatabase(context).ingredienteDao()

    // Funciones para Receta
    suspend fun getAllRecetas(): List<Receta> {
        return recetaDao.getAllRecetas()
    }

    suspend fun addReceta(receta: Receta) {
        recetaDao.insertReceta(receta)
    }

    suspend fun updateReceta(receta: Receta) {
        recetaDao.updateReceta(receta)
    }

    suspend fun deleteReceta(receta: Receta) {
        recetaDao.deleteReceta(receta)
    }

    // Funciones para Paso
    suspend fun getPasosByRecetaId(recetaId: Long): List<Paso> {
        return pasoDao.getPasosByRecetaId(recetaId)
    }

    suspend fun addPaso(paso: Paso) {
        pasoDao.insertPaso(paso)
    }

    suspend fun updatePaso(paso: Paso) {
        pasoDao.updatePaso(paso)
    }

    suspend fun deletePaso(paso: Paso) {
        pasoDao.deletePaso(paso)
    }

    // Funciones para Ingrediente
    suspend fun getIngredientesByRecetaId(recetaId: Long): List<Ingrediente> {
        return ingredienteDao.getIngredientesByRecetaId(recetaId)
    }

    suspend fun addIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.insertIngrediente(ingrediente)
    }

    suspend fun updateIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.updateIngrediente(ingrediente)
    }

    suspend fun deleteIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.deleteIngrediente(ingrediente)
    }
}
