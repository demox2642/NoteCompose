package com.example.composenotes.ui.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composenotes.R
import com.example.domain.models.Notes

@Composable
fun ListItem(
    note: Notes,
    modifier: Modifier,
    deleteNote: (Long) -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ConstraintLayout {
                    val (backButton, text) = createRefs()
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .constrainAs(text) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            note.name, style = MaterialTheme.typography.h6,

                        )
                    }
                    OutlinedButton(
                        onClick = { deleteNote(note.id!!) },
                        modifier = Modifier
                            .size(35.dp)
                            .padding(8.dp)
                            .constrainAs(backButton) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_plus),
                            contentDescription = stringResource(id = R.string.delete)
                        )
                    }
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
                    Text(text = note.note_text, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
