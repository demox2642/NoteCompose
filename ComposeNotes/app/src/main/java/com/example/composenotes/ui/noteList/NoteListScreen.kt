package com.example.composenotes.ui.noteList


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenotes.R
import com.example.domain.models.Notes
import java.util.*

@Composable
fun NoteListScreen() {
    NoteList()
}

@Composable
@Preview
fun NoteList() {
    val date = Calendar.getInstance().timeInMillis
    val list = listOf(
        Notes(1, "1111", "1111xzcvzcvxzcxcvasdfva", date, null, null),
        Notes(2, "2222", "2222xzcvzcvxzcxcvasdfva", date, null, null),
        Notes(3, "3333", "3333xzcvzcvxzcxcvasdfva", date, null, null),
        Notes(4, "4444", "4444xzcvzcvxzcxcvasdfva", date, null, null)
    )

    NoteListContent(noteList = list)
}

@OptIn(
    ExperimentalFoundationApi::class,
    com.google.accompanist.permissions.ExperimentalPermissionsApi::class
)
@Composable
fun NoteListContent(noteList: List<Notes>) {
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
                onClick = { /*do something*/ },
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
                    )
                }
            }
        }

    )
}
