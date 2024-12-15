package com.simon.proyectofinalut4.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredientes",
    foreignKeys = [ForeignKey(
        entity = Receta::class,
        parentColumns = ["id"],
        childColumns = ["recetaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Ingrediente(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val cantidad: String,
    @ColumnInfo(index = true) val recetaId: Long
)
