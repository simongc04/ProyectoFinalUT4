package com.simon.proyectofinalut4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pasos")
data class Paso(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val descripcion: String,
    val recetaId: Long // Relación con la receta
)
