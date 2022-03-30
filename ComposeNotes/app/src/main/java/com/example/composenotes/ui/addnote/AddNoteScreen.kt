package com.example.composenotes.ui.addnote

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.composenotes.R
import com.example.composenotes.dialogs.CustomAllertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import io.dar.base_ui.attachphotoandimage.EMPTY_IMAGE_URI
import io.dar.base_ui.attachphotoandimage.GallerySelect
import org.koin.androidx.compose.get

@Composable
fun AddNoteScreen() {
    AddNoteContent()
}

@Preview
@Composable
fun AddNotePreview() {
    AddNoteContent()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddNoteContent(viewModel: AddNoteViewModel = get()) {
    var name by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    var showGallerySelect by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }

    val openDialog = remember { mutableStateOf(false) }

    val state = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val closeDialog: () -> Unit = {
        openDialog.value = false
    }

    val changePermissionState: () -> Unit = {
        state.launchPermissionRequest()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                FloatingActionButton(
                    onClick = { openDialog.value = true },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    Icon(painterResource(R.drawable.ic_add_a_photo), stringResource(id = R.string.add_image))
                }

                FloatingActionButton(
                    onClick = { /*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)

                ) {
                    Icon(painterResource(R.drawable.ic_link), stringResource(id = R.string.save))
                }

                FloatingActionButton(
                    onClick = { viewModel.addNote(name = name, note_text = noteText) },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)

                ) {
                    Icon(Icons.Default.Done, stringResource(id = R.string.save))
                }
            }
        },
        content = {
            Column() {
                Row(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(id = R.string.name)) }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text(stringResource(id = R.string.note_text)) }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Test()
                if (openDialog.value) {

                    if (state.hasPermission) {
                        GallerySelect(
                            onImageUri = { uri ->
                                showGallerySelect = false
                                imageUri = uri
                            }
                        )
                    } else {

                        Dialog(
                            onDismissRequest = {},
                            content = {
                                CustomAllertDialog(
                                    title = stringResource(id = R.string.permission_title),
                                    message = stringResource(id = R.string.permission_message),
                                    confermButtonText = stringResource(id = R.string.permission_conferm_bt),
                                    dismissButtonText = stringResource(id = R.string.permission_dismiss_bt),
                                    closeDialog = closeDialog,
                                    changePermissionState = changePermissionState
                                )
                            }
                        )
                    }
                }

                if (imageUri != EMPTY_IMAGE_URI) {
                    Log.e("ImageMessageDialog", "imageUri = $imageUri")
                    viewModel.rememberImage(imageUri.toString())
//                    Dialog(
//                        onDismissRequest = {},
//                        content = {
//                            ImageMessageDialog(
//                                closeDialog = {
//                                    imageUri = EMPTY_IMAGE_URI
//                                    openDialog.value = false
//                                },
//                                uri = imageUri.toString()
//
//                            )
//                        }
//                    )
                }
            }
        }
    )
}

private var imageUriState = mutableStateOf<Uri?>(Uri.parse("content://com.android.providers.media.documents/document/image%3A33"))

@Composable
fun Test() {
    val imageData = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    imageData.value = imageUriState.value
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            imageData.let {
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                val uri = it.value
                Log.e("Test", "uri = $uri")
                if (uri != null) {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, uri)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Card(modifier = Modifier.size(100.dp)) {
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                // modifier = Modifier.size(200.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
