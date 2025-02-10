package com.simon.proyectofinalut4


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.simon.proyectofinalut4.ui.theme.ProyectoFinalUT4Theme
import com.simon.proyectofinalut4.view.RecetaViewModel
import com.simon.proyectofinalut4.viewModel.RecetaListScreen

class MainActivity : ComponentActivity() {
    private val recetaViewModel: RecetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalUT4Theme {
                RecetaListScreen(viewModel = recetaViewModel,)
            }
        }
    }
}
