package com.altoservicios.centros.presentation.screens.client.photo.components

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.imageUrl
import com.altoservicios.centros.presentation.components.openWhatsApp
import com.altoservicios.centros.presentation.screens.client.photo.ClientPhotoViewModel
import java.io.File

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ClientPhotoContent(
    navController: NavHostController,
    vm: ClientPhotoViewModel
) {
    val context = LocalContext.current
    var showDeleteConfirmation by rememberSaveable { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        val file = vm.pendingFile
        if (success && file != null) vm.onImageCaptured(file)
    }

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    LaunchedEffect(vm.finished) {
        if (vm.finished) navController.popBackStack()
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text(stringResource(R.string.confirm_delete_photo_title)) },
            text = { Text(stringResource(R.string.confirm_delete_photo_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                        vm.delete()
                    },
                    enabled = !vm.isLoading
                ) {
                    Text(stringResource(R.string.btn_delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            }
        )
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                val preview: Any? = vm.imageFile ?: imageUrl(vm.photo?.path)
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.5f)),
                    model = preview,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (vm.photo == null) {
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.btn_take_picture),
                        icon = Icons.Default.CameraAlt,
                        onClick = {
                            val directory = File(context.cacheDir, "images").apply { mkdirs() }
                            val file =
                                File(directory, "photo_${System.currentTimeMillis()}.jpg")
                            vm.pendingFile = file
                            cameraLauncher.launch(
                                FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    file
                                )
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                DefaultTextField(
                    label = stringResource(R.string.label_title),
                    value = vm.title,
                    onValueChange = { vm.title = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DefaultTextField(
                    label = stringResource(R.string.label_description),
                    value = vm.description,
                    onValueChange = { vm.description = it },
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.weight(1f), text = "Imagen principal")
                    Switch(checked = vm.principal, onCheckedChange = { vm.principal = it })
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
        ) {
            if (vm.photo != null) {
                DefaultButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_save),
                    icon = Icons.Default.Save,
                    onClick = { vm.update() }
                )
                Spacer(modifier = Modifier.width(12.dp))
                DefaultButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_delete),
                    icon = Icons.Default.Delete,
                    color = Color.Red,
                    enabled = !vm.isLoading,
                    onClick = { showDeleteConfirmation = true }
                )
            } else {
                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.btn_save),
                    icon = Icons.Default.Save,
                    onClick = { vm.store() }
                )
            }
        }
    }
}
