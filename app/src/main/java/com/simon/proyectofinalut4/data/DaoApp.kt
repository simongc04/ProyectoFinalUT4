package com.simon.proyectofinalut4.data

import androidx.room.*

@Dao
interface DaoReceta {
    @Insert suspend fun insertarReceta(receta: Receta)
    @Query("SELECT * FROM recetas") suspend fun obtenerTodasLasRecetas(): List<Receta>
    @Update suspend fun actualizarReceta(receta: Receta)
    @Delete suspend fun eliminarReceta(receta: Receta)
}

@Dao
interface DaoIngrediente {
    @Insert suspend fun insertarIngrediente(ingrediente: Ingrediente)
    @Query("SELECT * FROM ingredientes WHERE recetaId = :recetaId") suspend fun obtenerIngredientesPorReceta(recetaId: Int): List<Ingrediente>
    @Delete suspend fun eliminarIngrediente(ingrediente: Ingrediente)
}

@Dao
interface DaoCategoria {
    @Insert suspend fun insertarCategoria(categoria: Categoria)
    @Query("SELECT * FROM categorias") suspend fun obtenerTodasLasCategorias(): List<Categoria>
}
