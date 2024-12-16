package com.simon.proyectofinalut4.view

import android.content.Context
import com.simon.proyectofinalut4.data.AppDatabase
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.PasoDao
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.data.RecetaDao



class RecetaRepository(context: Context) {
    private val recetaDao: RecetaDao = AppDatabase.getDatabase(context).recetaDao()


    // Función para obtener todas las recetas
    suspend fun getAllRecetas(): List<Receta> {
        return recetaDao.getAllRecetas()
    }

    // Función para agregar una receta
    suspend fun addReceta(receta: Receta) {
        recetaDao.insertReceta(receta)
    }

    // Función para actualizar una receta
    suspend fun updateReceta(receta: Receta) {
        recetaDao.updateReceta(receta)
    }

    // Función para eliminar una receta
    suspend fun deleteReceta(receta: Receta) {
        recetaDao.deleteReceta(receta)
    }

}
