package com.simon.proyectofinalut4

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.view.RecetaViewModel
import com.simon.proyectofinalut4.view.RecetaViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaListScreen(viewModel: RecetaViewModel) {
    val recetas = viewModel.recetas.observeAsState(emptyList())
    val openDialog = remember { mutableStateOf(false) }
    val recetaToEdit = remember { mutableStateOf<Receta?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recetas de Comida") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    recetaToEdit.value = null // Al hacer clic, queremos agregar una nueva receta
                    openDialog.value = true
                },
                content = { Text("+") }
            )
        },
        content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(recetas.value) { receta ->
                    RecetaItem(
                        receta = receta,
                        onEdit = { recetaToEdit.value = it; openDialog.value = true },
                        onDelete = {
                            viewModel.deleteReceta(it)
                        }
                    )
                }
            }

            if (openDialog.value) {
                RecetaDialog(
                    receta = recetaToEdit.value,
                    onSave = { receta ->
                        if (recetaToEdit.value == null) {
                            viewModel.addReceta(receta) // Añadir receta
                        } else {
                            viewModel.updateReceta(receta) // Editar receta
                        }
                        openDialog.value = false // Cerrar el diálogo
                    },
                    onCancel = { openDialog.value = false } // Cancelar acción
                )
            }
        }
    )
}


@Composable
fun RecetaDialog(
    receta: Receta?,
    onSave: (Receta) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf(receta?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(receta?.descripcion ?: "") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (receta == null) "Agregar receta" else "Editar receta") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Agregar Receta") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(Receta(nombre = nombre, descripcion = descripcion))
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun RecetaItem(
    receta: Receta,
    onEdit: (Receta) -> Unit,
    onDelete: (Receta) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = receta.nombre)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = receta.descripcion)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(receta) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar receta")
                }
                IconButton(onClick = { onDelete(receta) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar receta")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // Aquí solo mostramos el componente de ejemplo para la vista previa sin necesidad del ViewModel real
    TaskApp()
}

@Composable
fun TaskApp() {
    val context = LocalContext.current
    val applicationContext = context.applicationContext as Application // Cast a Application

    // Usar la fábrica del ViewModel pasando el Application
    val viewModel: RecetaViewModel = viewModel(factory = RecetaViewModelFactory(applicationContext))

    // Ahora pasa el ViewModel a tu Composable RecetaListScreen
    RecetaListScreen(viewModel = viewModel)
}
