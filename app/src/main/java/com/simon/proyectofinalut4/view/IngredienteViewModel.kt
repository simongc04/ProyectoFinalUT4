package com.simon.proyectofinalut4.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simon.proyectofinalut4.data.Ingrediente
import kotlinx.coroutines.launch

class IngredienteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = IngredienteRepository(application)

    private val _ingredientes = MutableLiveData<List<Ingrediente>>()
    val ingredientes: LiveData<List<Ingrediente>> get() = _ingredientes

    fun loadIngredientesByRecetaId(recetaId: Long) {
        viewModelScope.launch {
            _ingredientes.value = repository.getIngredientesByRecetaId(recetaId)
        }
    }

    fun addIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.addIngrediente(ingrediente)
            loadIngredientesByRecetaId(ingrediente.recetaId)
        }
    }

    fun updateIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.updateIngrediente(ingrediente)
            loadIngredientesByRecetaId(ingrediente.recetaId)
        }
    }

    fun deleteIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.deleteIngrediente(ingrediente)
            loadIngredientesByRecetaId(ingrediente.recetaId)
        }
    }
}
