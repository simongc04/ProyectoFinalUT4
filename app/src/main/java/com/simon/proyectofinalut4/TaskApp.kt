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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simon.proyectofinalut4.data.Ingrediente
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.view.RecetaViewModel
import com.simon.proyectofinalut4.view.RecetaViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaListScreen(viewModel: RecetaViewModel) {
    val recetas = viewModel.recetas.observeAsState(emptyList())
    val openDialog = remember { mutableStateOf(false) }
    val recetaToEdit = remember { mutableStateOf<Receta?>(null) }

    // Cargar recetas al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadRecetas()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recetas de Comida") })
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Lista de recetas
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

                Button(
                    onClick = {
                        recetaToEdit.value = null // Preparar para añadir nueva receta
                        openDialog.value = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp) // Margen inferior
                        .fillMaxWidth(0.8f), // Tamaño del botón (80% del ancho)
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        text = "Añadir Receta",
                    )
                }


                if (openDialog.value) {
                    RecetaDialog(
                        receta = recetaToEdit.value,
                        onSave = { receta ->
                            if (recetaToEdit.value == null) {
                                viewModel.addReceta(receta)
                            } else {
                                viewModel.updateReceta(receta)
                            }
                            openDialog.value = false
                        },
                        onCancel = { openDialog.value = false }
                    )
                }
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
    var pasosPrincipales by remember { mutableStateOf(receta?.pasosPrincipales ?: "") } // Nuevo campo

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
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pasosPrincipales,
                    onValueChange = { pasosPrincipales = it },
                    label = { Text("Pasos Principales") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        Receta(
                            id = receta?.id ?: 0,
                            nombre = nombre,
                            descripcion = descripcion,
                            pasosPrincipales = pasosPrincipales // Guardar el nuevo campo
                        )
                    )
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${receta.nombre}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Receta: ${receta.descripcion}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Pasos Principales: ${receta.pasosPrincipales}")
            Spacer(modifier = Modifier.height(10.dp))
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
    val applicationContext = context.applicationContext as Application

    val viewModel: RecetaViewModel = viewModel(factory = RecetaViewModelFactory(applicationContext))

    RecetaListScreen(viewModel = viewModel)
}
