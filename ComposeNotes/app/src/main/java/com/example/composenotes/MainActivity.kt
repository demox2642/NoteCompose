package com.example.composenotes

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.composenotes.navigation.Navigation
import com.example.composenotes.ui.theme.ComposeNotesTheme
import com.example.composenotes.utils.SPStrings
import com.example.composenotes.utils.StorageUtils
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    companion object {
        lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentSenderLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == RESULT_OK) {
                // Addressing API 29 (Android 10) tricky behaviour
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                    val name = SPStrings.preferences
                    val sp = getSharedPreferences(name, Context.MODE_PRIVATE)
                    val spKey = SPStrings.API29_delete
                    val deletedImage = sp.getString(spKey, null)
                    if (deletedImage != null) {
                        val deletedImageUri = Uri.parse(deletedImage)
                        lifecycleScope.launch {
                            StorageUtils.delete(this@MainActivity, deletedImageUri ?: return@launch)
                        }
                    }
                }
            }
        }
        setContent {
            ComposeNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}
