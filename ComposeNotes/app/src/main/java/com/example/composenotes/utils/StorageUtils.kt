package com.example.composenotes.utils

import android.Manifest
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.PermissionChecker
import com.example.composenotes.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StorageUtils {

    companion object {

        private val imagesURIs = mutableStateListOf<Uri>()

        private var queryOrder = "DESC"

        fun setQueryOrder(order: String, context: Context) {
            queryOrder =
                if (order == "ASC" || order == "DESC") order
                else "DESC"
            acquireImageURIs(context)
        }

        fun hasReadStoragePermission(context: Context): Boolean {
            val permission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    PermissionChecker.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            return permission == PackageManager.PERMISSION_GRANTED
        }

        private fun acquireImageURIs(context: Context) {

            imagesURIs.clear()

            if (!hasReadStoragePermission(context = context))
                throw SecurityException("context.getString()")

            // Setting un the query
            val columns = arrayOf(MediaStore.Images.Media._ID)
            val orderBy = MediaStore.Images.Media.DATE_TAKEN

            val imageCursor: Cursor? = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                null, null, "$orderBy $queryOrder"
            )

            val columnIndex = imageCursor!!.getColumnIndex(MediaStore.Images.Media._ID)

            while (imageCursor.moveToNext()) {
                val id = imageCursor.getLong(columnIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                imagesURIs.add(imageUri)
            }

            imageCursor.close()
        }

        @ExperimentalMaterialApi
        @ExperimentalFoundationApi
        suspend fun delete(context: Context, uri: Uri) {
            withContext(Dispatchers.IO) {
                try {
                    context.contentResolver.delete(uri, null, null)
                } catch (e: SecurityException) {
                    Log.d("EXCEPTION", e.toString())
                    val intentSender = when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                            MediaStore.createDeleteRequest(context.contentResolver, listOf(uri)).intentSender
                        }
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                            val recoverableSecurityException = e as? RecoverableSecurityException
                            recoverableSecurityException?.userAction?.actionIntent?.intentSender
                        }
                        else -> null
                    }
                    intentSender?.let { sender ->
                        MainActivity.intentSenderLauncher.launch(
                            IntentSenderRequest.Builder(sender).build()
                        )
                    }
                } finally {
                    // Refresh the imageURIs array
                    acquireImageURIs(context = context)
                }
            }
        }
    }
}
