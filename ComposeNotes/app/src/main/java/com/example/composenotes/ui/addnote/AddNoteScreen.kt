package com.example.composenotes.ui.addnote

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.composenotes.R
import com.example.composenotes.dialogs.CustomAllertDialog
import com.example.composenotes.dialogs.CustomErrorDialog
import com.example.composenotes.utils.SPStrings
import com.example.composenotes.utils.StorageUtils
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
    var nameValidation by remember {
        mutableStateOf(false)
    }
    var noteTextValidation by remember {
        mutableStateOf(false)
    }
    var noteText by remember { mutableStateOf("") }
    val imageData by viewModel.imageList.collectAsState()
    val showAlertDialog by viewModel.showAlertDialog.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            viewModel.rememberImage(Uri.parse(it.toString().replace("content://com.android.providers.media.documents/document/image%3A", "content://media/external/images/media/")).toString())
        }

//    var showGallerySelect by remember { mutableStateOf(false) }
//    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
//
//    val openDialog = remember { mutableStateOf(false) }
//
//    val state = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)

    val context = LocalContext.current
    val actualPermission = StorageUtils.hasReadStoragePermission(context)
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launcher.launch(
                    "image/*"
                )
            } else {
                Log.e("Test", "requestPermissionLauncher isGranted=$isGranted")
            }
        }
    val changePermissionState: () -> Unit = {
        requestPermissionLauncher.launch(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        viewModel.changeVisibleAlertDialog()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                FloatingActionButton(
                    onClick = {
                        if (actualPermission) {
                            launcher.launch(
                                "image/*"
                            )
                        } else {
                            viewModel.changeVisibleAlertDialog()
                        }
                    },
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
                    onClick = {
                        if (name.isEmpty() || noteText.isEmpty()) {
                            if (name.isEmpty()) {
                                nameValidation = true
                            }
                            if (noteText.isEmpty()) {
                                noteTextValidation = true
                            }
                        } else {
                            viewModel.addNote(name = name, note_text = noteText)
                        }
                    },
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
                        onValueChange = {
                            name = it
                            nameValidation = false
                        },
                        label = { Text(stringResource(id = R.string.name)) },
                        isError = nameValidation,
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = noteText,
                    onValueChange = {
                        noteText = it
                        noteTextValidation = false
                    },
                    isError = noteTextValidation,

                    label = { Text(stringResource(id = R.string.note_text)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (showAlertDialog) {
                    Dialog(
                        onDismissRequest = {},
                        content = {
                            CustomAllertDialog(
                                title = stringResource(id = R.string.permission_title),
                                message = stringResource(id = R.string.permission_message),
                                confermButtonText = stringResource(id = R.string.permission_conferm_bt),
                                dismissButtonText = stringResource(id = R.string.permission_dismiss_bt),
                                closeDialog = {
                                    viewModel.changeVisibleAlertDialog()
                                    viewModel.changeVisibleErrorDialog()
                                },
                                changePermissionState = changePermissionState
                            )
                        }
                    )
                }
                if (showErrorDialog) {
                    CustomErrorDialog(
                        title = stringResource(id = R.string.permission_error),
                        message = stringResource(id = R.string.permission_error_message),
                        confermButtonText = stringResource(id = R.string.exit),
                        closeDialog = { viewModel.changeVisibleErrorDialog() }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (imageData.isNotEmpty()) {
                    LazyRow(content = {
                        items(imageData.size) { it ->
                            ListItem(Uri.parse(imageData[it]))
                        }
                    })
                }
            }
        }
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListItem(uri: Uri) {
    val context = LocalContext.current
    val actualPermission = StorageUtils.hasReadStoragePermission(context)
    val name = SPStrings.preferences
    val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    if (actualPermission) {
        Log.e("Test", "permission is true uri = $uri")
        val order = sharedPref.getString(SPStrings.default_order, "DESC")!!
        StorageUtils.setQueryOrder(order, context = context)
        // if (uri != null) {
        CreateGrid(uri, 150.dp)
        // }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CreateGrid(photos: Uri, size: Dp) {
    Log.e("Test", "permission is true photos = $photos")
    Card(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
    ) {
        if (photos.toString().startsWith("content://media/external/")) {
            Image(
                painter = rememberCoilPainter(
                    request = photos
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .clickable {
                    }
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = rememberCoilPainter(photos),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .clickable {
                    }
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
