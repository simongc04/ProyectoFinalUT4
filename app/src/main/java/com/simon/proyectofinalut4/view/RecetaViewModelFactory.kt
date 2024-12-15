package com.simon.proyectofinalut4.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class RecetaViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecetaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
