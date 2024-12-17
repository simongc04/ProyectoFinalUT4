package com.simon.proyectofinalut4.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simon.proyectofinalut4.view.PasoViewModel

class PasoViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
