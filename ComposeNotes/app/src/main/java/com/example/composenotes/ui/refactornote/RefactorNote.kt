package com.example.composenotes.ui.refactornote

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.composenotes.R
import com.example.composenotes.dialogs.CustomAlertDialog
import com.example.composenotes.dialogs.CustomEnterLincDialog
import com.example.composenotes.dialogs.CustomErrorDialog
import com.example.composenotes.navigation.MainScreens
import com.example.composenotes.ui.addnote.CreateGrid
import com.example.composenotes.utils.SPStrings
import com.example.composenotes.utils.StorageUtils
import com.example.composenotes.utils.toNotes
import com.example.domain.models.Notes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.androidx.compose.get

@Composable
fun RefactorNote(noteArg: String, navController: NavHostController) {

    val note = noteArg.toNotes()

    if (note != null) {
        RefactorAddNoteContent(note, navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RefactorAddNoteContent(notes: Notes, navController: NavHostController, viewModel: RefactorNoteViewModel = get()) {
    if (!notes.images.isNullOrEmpty()) {
        viewModel.rememberImageList(notes.images!!)
    }

    var name by remember { mutableStateOf(notes.name) }
    var nameValidation by remember {
        mutableStateOf(false)
    }
    var noteTextValidation by remember {
        mutableStateOf(false)
    }
    var noteText by remember { mutableStateOf(notes.note_text) }
    val imageData by viewModel.imageList.collectAsState()
    val showAlertDialog by viewModel.showAlertDialog.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()
    val showEnterLincDialog by viewModel.showEnterLincDialog.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            viewModel.rememberImage(Uri.parse(it.toString().replace("content://com.android.providers.media.documents/document/image%3A", "content://media/external/images/media/")).toString())
        }

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
                Log.e("Permission", " Error: requestPermissionLauncher isGranted=$isGranted")
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
                    onClick = { viewModel.changeVisibleEnterLinc() },
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
//                            viewModel.addNote(name = name, note_text = noteText)
                            navController.navigate(MainScreens.NoteListScreen.route)
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
                            CustomAlertDialog(
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
                        closeDialog = { viewModel.changeVisibleErrorDialog() },
                    )
                }
                if (showEnterLincDialog) {
                    CustomEnterLincDialog(
                        confermButtonText = stringResource(id = R.string.save),
                        dismissButtonText = stringResource(id = R.string.permission_dismiss_bt),
                        closeDialog = { viewModel.changeVisibleEnterLinc() },
                        saveLinc = {
                            viewModel.rememberImage(it)
                            viewModel.changeVisibleEnterLinc()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (imageData.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.padding(8.dp),
                        content = {
                            items(imageData.size) { it ->
                                RefactorItem(Uri.parse(imageData[it])) { viewModel.deleteImage(it) }
                            }
                        }
                    )
                }
            }
        }
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RefactorItem(uri: Uri, delete: () -> Unit) {
    val context = LocalContext.current
    val actualPermission = StorageUtils.hasReadStoragePermission(context)
    val name = SPStrings.preferences
    val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    if (actualPermission) {
        val order = sharedPref.getString(SPStrings.default_order, "DESC")!!
        StorageUtils.setQueryOrder(order, context = context)
          CreateGrid(uri, 150.dp, delete)
    }
}
