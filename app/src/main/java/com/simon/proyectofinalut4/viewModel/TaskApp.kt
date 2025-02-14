package com.simon.proyectofinalut4.viewModel

import android.app.Application
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simon.proyectofinalut4.data.Ingrediente
import com.simon.proyectofinalut4.data.Paso
import com.simon.proyectofinalut4.data.Receta
import com.simon.proyectofinalut4.view.ViewModel
import com.simon.proyectofinalut4.view.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaListScreen(viewModel: ViewModel) {
    val recetas = viewModel.recetas.observeAsState(emptyList())
    val openDialog = remember { mutableStateOf(false) }
    val recetaToEdit = remember { mutableStateOf<Receta?>(null) }

    // Cargar recetas al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadRecetas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recetas de Comida", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Red)
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                // Lista de recetas
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(recetas.value) { receta ->
                        RecetaItem(
                            receta = receta,
                            viewModel = viewModel,
                            onEdit = { recetaToEdit.value = it; openDialog.value = true },
                            onDelete = { viewModel.deleteReceta(it) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                ExtendedFloatingActionButton(
                    onClick = {
                        recetaToEdit.value = null // Preparar para añadir nueva receta
                        openDialog.value = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    icon = { Icon(Icons.Default.Add, contentDescription = "Añadir Receta", tint = Color.White) },
                    text = { Text("Añadir Receta", color = Color.White) },
                    containerColor = Color.Red,
                    contentColor = Color.White
                )

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

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (receta == null) "Agregar receta" else "Editar receta") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(Receta(id = receta?.id ?: 0, nombre = nombre)) }) {
                Text("Guardar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar", color = Color.Red)
            }
        }
    )
}

@Composable
fun RecetaItem(
    receta: Receta,
    viewModel: ViewModel,
    onEdit: (Receta) -> Unit,
    onDelete: (Receta) -> Unit
) {
    val pasos = viewModel.pasos.observeAsState(emptyMap()).value[receta.id] ?: emptyList()
    val ingredientes = viewModel.ingredientes.observeAsState(emptyMap()).value[receta.id] ?: emptyList()
    val openPasoDialog = remember { mutableStateOf(false) }
    val pasoToEdit = remember { mutableStateOf<Paso?>(null) }
    val openIngredienteDialog = remember { mutableStateOf(false) }
    val ingredienteToEdit = remember { mutableStateOf<Ingrediente?>(null) }

    // Cargar pasos e ingredientes al iniciar
    LaunchedEffect(receta.id) {
        viewModel.loadPasos(receta.id)
        viewModel.loadIngredientes(receta.id)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = receta.nombre, style = MaterialTheme.typography.headlineSmall, color = Color.Red)
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium, color = Color.Red)
            ingredientes.forEach { ingrediente ->
                IngredienteItem(
                    ingrediente = ingrediente,
                    onEdit = { ingredienteToEdit.value = it; openIngredienteDialog.value = true },
                    onDelete = { viewModel.deleteIngrediente(it) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Pasos:", style = MaterialTheme.typography.titleMedium, color = Color.Red)
            pasos.forEach { paso ->
                PasoItem(
                    paso = paso,
                    onEdit = { pasoToEdit.value = it; openPasoDialog.value = true },
                    onDelete = { viewModel.deletePaso(it) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(receta) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar receta", tint = Color.Red)
                }
                IconButton(onClick = { onDelete(receta) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar receta", tint = Color.Red)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        ingredienteToEdit.value = Ingrediente(nombre = "", cantidad = "", recetaId = receta.id)
                        openIngredienteDialog.value = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Añadir Ingrediente", color = Color.White)
                }
                Button(
                    onClick = {
                        pasoToEdit.value = Paso(descripcion = "", recetaId = receta.id)
                        openPasoDialog.value = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Añadir Paso", color = Color.White)
                }
            }
        }
    }

    if (openPasoDialog.value) {
        PasoDialog(
            paso = pasoToEdit.value,
            onSave = { paso ->
                if (pasoToEdit.value?.id == 0L) {
                    viewModel.addPaso(paso)
                } else {
                    viewModel.updatePaso(paso)
                }
                openPasoDialog.value = false
            },
            onCancel = { openPasoDialog.value = false }
        )
    }

    if (openIngredienteDialog.value) {
        IngredienteDialog(
            ingrediente = ingredienteToEdit.value,
            onSave = { ingrediente ->
                if (ingredienteToEdit.value?.id == 0L) {
                    viewModel.addIngrediente(ingrediente)
                } else {
                    viewModel.updateIngrediente(ingrediente)
                }
                openIngredienteDialog.value = false
            },
            onCancel = { openIngredienteDialog.value = false }
        )
    }
}

@Composable
fun PasoItem(
    paso: Paso,
    onEdit: (Paso) -> Unit,
    onDelete: (Paso) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = paso.descripcion, style = MaterialTheme.typography.bodyMedium, color = Color.Red)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(paso) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar paso", tint = Color.Red)
                }
                IconButton(onClick = { onDelete(paso) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar paso", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun PasoDialog(
    paso: Paso?,
    onSave: (Paso) -> Unit,
    onCancel: () -> Unit
) {
    var descripcion by remember { mutableStateOf(paso?.descripcion ?: "") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (paso == null) "Agregar paso" else "Editar paso") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(Paso(id = paso?.id ?: 0, descripcion = descripcion, recetaId = paso?.recetaId ?: 0)) }) {
                Text("Guardar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar", color = Color.Red)
            }
        }
    )
}

@Composable
fun IngredienteItem(
    ingrediente: Ingrediente,
    onEdit: (Ingrediente) -> Unit,
    onDelete: (Ingrediente) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${ingrediente.nombre}", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
            Text(text = "Cantidad: ${ingrediente.cantidad}", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(ingrediente) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar ingrediente", tint = Color.Red)
                }
                IconButton(onClick = { onDelete(ingrediente) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar ingrediente", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun IngredienteDialog(
    ingrediente: Ingrediente?,
    onSave: (Ingrediente) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf(ingrediente?.nombre ?: "") }
    var cantidad by remember { mutableStateOf(ingrediente?.cantidad ?: "") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (ingrediente == null) "Agregar ingrediente" else "Editar ingrediente") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(Ingrediente(id = ingrediente?.id ?: 0, nombre = nombre, cantidad = cantidad, recetaId = ingrediente?.recetaId ?: 0)) }) {
                Text("Guardar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar", color = Color.Red)
            }
        }
    )
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

    val viewModel: ViewModel = viewModel(factory = ViewModelFactory(applicationContext))

    RecetaListScreen(viewModel = viewModel)
}
