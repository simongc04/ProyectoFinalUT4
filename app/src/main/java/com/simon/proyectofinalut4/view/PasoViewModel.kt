package com.simon.proyectofinalut4.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simon.proyectofinalut4.data.Paso
import kotlinx.coroutines.launch

class PasoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PasoRepository(application)

    private val _pasos = MutableLiveData<List<Paso>>()
    val pasos: LiveData<List<Paso>> get() = _pasos

    fun loadPasosByRecetaId(recetaId: Long) {
        viewModelScope.launch {
            _pasos.value = repository.getPasosByRecetaId(recetaId)
        }
    }

    fun addPaso(paso: Paso) {
        viewModelScope.launch {
            repository.addPaso(paso)
            loadPasosByRecetaId(paso.recetaId)
        }
    }

    fun updatePaso(paso: Paso) {
        viewModelScope.launch {
            repository.updatePaso(paso)
            loadPasosByRecetaId(paso.recetaId)
        }
    }

    fun deletePaso(paso: Paso) {
        viewModelScope.launch {
            repository.deletePaso(paso)
            loadPasosByRecetaId(paso.recetaId)
        }
    }
}
