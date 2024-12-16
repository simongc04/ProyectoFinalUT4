package com.simon.proyectofinalut4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Receta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recetaDao(): RecetaDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "receta_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

