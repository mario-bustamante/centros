package com.altoservicios.centros.presentation.screens.admin.photo

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.FormTextField
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.imageUrl
import java.io.File

@Composable
fun AdminPhotoScreen(navController: NavHostController, vm: AdminPhotoViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        val file = vm.pendingFile
        if (success && file != null) vm.onImageCaptured(file)
    }

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    LaunchedEffect(vm.finished) {
        if (vm.finished) navController.popBackStack()
    }

    Scaffold(
        topBar = { DefaultTopBar(title = "Fotografía", upAvailable = true, navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                val preview: Any? = vm.imageFile ?: imageUrl(vm.photo?.path)
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color.White.copy(alpha = 0.5f)),
                    model = preview,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    text = stringResource(R.string.btn_take_picture),
                    onClick = {
                        val directory = File(context.cacheDir, "images").apply { mkdirs() }
                        val file = File(directory, "photo_${System.currentTimeMillis()}.jpg")
                        vm.pendingFile = file
                        cameraLauncher.launch(
                            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                        )
                    }
                )

                FormTextField(
                    label = stringResource(R.string.label_title),
                    value = vm.title,
                    onValueChange = { vm.title = it }
                )
                FormTextField(
                    label = stringResource(R.string.label_description),
                    value = vm.description,
                    onValueChange = { vm.description = it },
                    singleLine = false
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.weight(1f), text = "Imagen principal")
                    Switch(checked = vm.principal, onCheckedChange = { vm.principal = it })
                }

                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.btn_save),
                    onClick = { vm.save() }
                )

                if (vm.photo != null) {
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = stringResource(R.string.btn_delete),
                        color = Color.Red,
                        onClick = { vm.delete() }
                    )
                }
            }

            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
