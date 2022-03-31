package com.example.composenotes.ui.notedetail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composenotes.R
import com.example.composenotes.navigation.MainScreens
import com.example.composenotes.utils.setAsJson
import com.example.composenotes.utils.toCalendarString
import com.example.domain.models.ImageForNote
import com.example.domain.models.Notes
import com.google.accompanist.coil.rememberCoilPainter
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.get

@Composable
fun NoteDetail(noteId: Long, navController: NavHostController, viewModel: NoteDetailViewModel = get()) {
    viewModel.getNote(noteId)
    val note by viewModel.note.collectAsState()
    val imageList by viewModel.imageList.collectAsState()
    if (note != null) {
        NoteContent(note!!, imageList, navController)
    }
}

@Composable
fun NoteContent(note: Notes, imageList: List<ImageForNote>?, navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Create, stringResource(id = R.string.add)) },
                text = { Text(stringResource(id = R.string.refactor)) },
                onClick = { navController.navigate(MainScreens.RefactorNote.withStringArgs(note.setAsJson())) },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier.padding(bottom = 4.dp, end = 4.dp)

            )
        },
        content = {
            Card() {
                Column(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            note.name, style = MaterialTheme.typography.h6,

                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_date) + note.add_date.toCalendarString(),
                            style = MaterialTheme.typography.h6,

                        )
                    }
                    if (note.refactor_date != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.refactor_date) + note.refactor_date!!.toCalendarString(),
                                style = MaterialTheme.typography.h6,

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {

                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                        ) {
                            Row() {
                                Text(text = note.note_text, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            if (!imageList.isNullOrEmpty()) {
                                LazyRow(
                                    modifier = Modifier.padding(8.dp),
                                    content = {
                                        items(imageList.size) { it ->
                                            NoteListItem(
                                                Uri.parse(
                                                    imageList[it].image_linc
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NoteListItem(photos: Uri?,) {

    Card(
        modifier = Modifier
            .size(150.dp)
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

            GlideImage(
                imageModel = photos,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.ic_net_error)
            )
        }
    }
}
