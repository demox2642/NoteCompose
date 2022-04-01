package com.example.composenotes.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composenotes.R

@Composable
fun CustomEnterLincDialog(
    modifier: Modifier = Modifier,
    confermButtonText: String,
    dismissButtonText: String,
    closeDialog: () -> Unit,
    saveLinc: (String) -> Unit
) {
    var lincText by remember { mutableStateOf("") }
    var linkTextValidation by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(15.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.enter_link),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = lincText,
                    onValueChange = {
                        lincText = it
                        linkTextValidation = false
                    },
                    isError = linkTextValidation,

                    label = { Text(stringResource(id = R.string.enter_link)) }
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(
                    onClick = closeDialog
                ) {

                    Text(
                        dismissButtonText,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    if (lincText.isEmpty()) {
                        linkTextValidation = true
                    } else (
                        saveLinc(lincText)
                        )
                }) {
                    Text(
                        confermButtonText,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}
