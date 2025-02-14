package com.simon.proyectofinalut4.view

import android.app.Application
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simon.proyectofinalut4.R
import com.simon.proyectofinalut4.data.Ingrediente
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.notify.NotificationUtils
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)
    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> get() = _recetas

    private val _pasos = MutableLiveData<Map<Long, List<Paso>>>()
    val pasos: LiveData<Map<Long, List<Paso>>> get() = _pasos

    private val _ingredientes = MutableLiveData<Map<Long, List<Ingrediente>>>()
    val ingredientes: LiveData<Map<Long, List<Ingrediente>>> get() = _ingredientes

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta)
    private val mediaPlayer2: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta2)
    private val mediaPlayer3: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta3)

    fun loadRecetas() {
        viewModelScope.launch {
            _recetas.value = repository.getAllRecetas()
        }
    }

    fun loadPasos(recetaId: Long) {
        viewModelScope.launch {
            val currentPasos = _pasos.value?.toMutableMap() ?: mutableMapOf()
            currentPasos[recetaId] = repository.getPasosByRecetaId(recetaId)
            _pasos.value = currentPasos
        }
    }

    fun loadIngredientes(recetaId: Long) {
        viewModelScope.launch {
            val currentIngredientes = _ingredientes.value?.toMutableMap() ?: mutableMapOf()
            currentIngredientes[recetaId] = repository.getIngredientesByRecetaId(recetaId)
            _ingredientes.value = currentIngredientes
        }
    }

    fun addReceta(receta: Receta) {
        viewModelScope.launch {
            repository.addReceta(receta)
            loadRecetas()
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()

                getApplication<Application>().let { context ->
                    NotificationUtils.showNotification(
                        context,
                        "Nueva Receta",
                        "Se ha creado: ${receta.nombre}"
                    )

                    Toast.makeText(context, "Receta '${receta.nombre}' añadida", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun updateReceta(receta: Receta) {
        viewModelScope.launch {
            repository.updateReceta(receta)
            loadRecetas()

            // Reproducir sonido al editar una receta
            if (!mediaPlayer3.isPlaying) {
                mediaPlayer3.start()
            }
        }
    }

    fun deleteReceta(receta: Receta) {
        viewModelScope.launch {
            repository.deleteReceta(receta)
            loadRecetas()

            if (!mediaPlayer2.isPlaying) {
                mediaPlayer2.start()  // Reproducir sonido al eliminar
            }
        }
    }

    fun addPaso(paso: Paso) {
        viewModelScope.launch {
            repository.addPaso(paso)
            loadPasos(paso.recetaId)
        }
    }

    fun updatePaso(paso: Paso) {
        viewModelScope.launch {
            repository.updatePaso(paso)
            loadPasos(paso.recetaId)
        }
    }

    fun deletePaso(paso: Paso) {
        viewModelScope.launch {
            repository.deletePaso(paso)
            loadPasos(paso.recetaId)
        }
    }

    fun addIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.addIngrediente(ingrediente)
            loadIngredientes(ingrediente.recetaId)
        }
    }

    fun updateIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.updateIngrediente(ingrediente)
            loadIngredientes(ingrediente.recetaId)
        }
    }

    fun deleteIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.deleteIngrediente(ingrediente)
            loadIngredientes(ingrediente.recetaId)
        }
    }

    override fun onCleared() {
        super.onCleared()

        // Liberar recursos del MediaPlayer cuando el ViewModel se destruye
        mediaPlayer.release()
        mediaPlayer2.release()
        mediaPlayer3.release()
    }
}
