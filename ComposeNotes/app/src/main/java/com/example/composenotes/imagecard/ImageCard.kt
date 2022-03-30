package com.example.composenotes.imagecard

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImageCard() {
    Test()
}

//@Preview
//@Composable
//fun ImageCardPreview() {
//
//    ImageCard("content://com.android.providers.media.documents/document/image%3A31")
//}
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
