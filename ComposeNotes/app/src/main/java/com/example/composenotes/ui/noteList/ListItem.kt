package com.example.composenotes.ui.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composenotes.R
import com.example.domain.models.Notes

@Composable
fun ListItem(
    note: Notes,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Column() {
            Row(modifier = Modifier.background(Color.Gray).fillMaxWidth()) {
                Text(stringResource(id = R.string.name), style = MaterialTheme.typography.h4, modifier = Modifier.padding(8.dp))
                Text(note.name, style = MaterialTheme.typography.h4, modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = note.note_text)
            }
        }
    }
}
