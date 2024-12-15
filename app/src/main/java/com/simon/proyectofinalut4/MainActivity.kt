package com.simon.proyectofinalut4


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import com.simon.proyectofinalut4.ui.theme.ProyectoFinalUT4Theme
import com.simon.proyectofinalut4.view.RecetaViewModel

class MainActivity : ComponentActivity() {
    private val recetaViewModel: RecetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalUT4Theme {
                RecetaListScreen(viewModel = recetaViewModel)
            }
        }
    }
}
