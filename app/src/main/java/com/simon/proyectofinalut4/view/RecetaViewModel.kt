package com.simon.proyectofinalut4.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.simon.proyectofinalut4.data.Receta
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecetaRepository = RecetaRepository(application)
    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> get() = _recetas

    fun loadRecetas() {
        viewModelScope.launch {
            _recetas.value = repository.getAllRecetas()
        }
    }

    fun addReceta(receta: Receta) {
        viewModelScope.launch {
            repository.addReceta(receta)
            loadRecetas() // recargar la lista
        }
    }

    fun updateReceta(receta: Receta) {
        viewModelScope.launch {
            repository.updateReceta(receta)
            loadRecetas()
        }
    }

    fun deleteReceta(receta: Receta) {
        viewModelScope.launch {
            repository.deleteReceta(receta)
            loadRecetas()
        }
    }
}

