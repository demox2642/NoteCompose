package com.example.composenotes.ui.noteList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composenotes.R
import com.example.composenotes.navigation.MainScreens
import com.example.domain.models.Notes
import org.koin.androidx.compose.get

@Composable
fun NoteListScreen(navController: NavHostController) {
    NoteList(navController)
}

@Composable
fun NoteList(navController: NavHostController, viewModel: NoteListViewModel = get()) {

    val noteList by viewModel.noteList.collectAsState()

    NoteListContent(noteList = noteList, viewModel::deleteNote, navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListContent(noteList: List<Notes>, deleteNote: (Long) -> Unit, navController: NavHostController) {
    val configuration = LocalConfiguration.current

    val dimensions = if (configuration.screenWidthDp <= 400) 2 else 3
    val sizeCard = (configuration.screenWidthDp - 8) / dimensions

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Add, stringResource(id = R.string.add)) },
                text = { Text(stringResource(id = R.string.add)) },
                onClick = { navController.navigate(MainScreens.AddNoteScreen.route) },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier.padding(bottom = 4.dp, end = 4.dp)

            )
        },
        content = {
            LazyVerticalGrid(
                cells = GridCells.Fixed(dimensions),
                modifier = Modifier.padding(8.dp)
            ) {
                items(noteList.size) { count ->
                    ListItem(
                        note = noteList[count],
                        modifier = Modifier
                            .size(sizeCard.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        deleteNote = deleteNote
                    )
                }
            }
        }

    )
}
