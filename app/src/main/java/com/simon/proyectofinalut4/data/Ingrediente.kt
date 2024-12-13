package com.simon.proyectofinalut4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredientes")
data class Ingrediente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val cantidad: String,
    val recetaId: Int
)
