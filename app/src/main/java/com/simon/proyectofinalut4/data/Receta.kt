package com.simon.proyectofinalut4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val descripcion: String
)

