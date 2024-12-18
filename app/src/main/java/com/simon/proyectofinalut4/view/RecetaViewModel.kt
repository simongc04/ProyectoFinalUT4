package com.simon.proyectofinalut4.view

import android.app.Application
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.simon.proyectofinalut4.notify.NotificationUtils
import com.simon.proyectofinalut4.R
import com.simon.proyectofinalut4.data.Receta
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecetaRepository = RecetaRepository(application)
    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> get() = _recetas

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta)
    private val mediaPlayer2: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta2)
    private val mediaPlayer3: MediaPlayer = MediaPlayer.create(application, R.raw.notificacion_receta3)

    fun loadRecetas() {
        viewModelScope.launch {
            _recetas.value = repository.getAllRecetas()
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
    } }


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

    override fun onCleared() {
        super.onCleared()

        // Liberar recursos del MediaPlayer cuando el ViewModel se destruye
        mediaPlayer.release()
        mediaPlayer2.release()
        mediaPlayer3.release()
    }
}
