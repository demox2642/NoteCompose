package com.example.composenotes.ui.refactornote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ImageForNote
import com.example.domain.models.Notes
import com.example.domain.usecase.AddNote
import com.example.domain.usecase.DeleteNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class RefactorNoteViewModel(
    private val addNote: AddNote,
    private val deleteNote: DeleteNote
) : ViewModel() {

    private val _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog

    fun changeVisibleAlertDialog() {
        _showAlertDialog.value = _showAlertDialog.value.not()
    }

    private val _showEnterLincDialog = MutableStateFlow(false)
    val showEnterLincDialog: StateFlow<Boolean> = _showEnterLincDialog

    fun changeVisibleEnterLinc() {
        _showEnterLincDialog.value = _showEnterLincDialog.value.not()
    }

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    fun changeVisibleErrorDialog() {
        _showErrorDialog.value = _showErrorDialog.value.not()
    }

    private val _imageList = MutableStateFlow<List<ImageForNote> >(emptyList())
    val imageList: StateFlow<List<ImageForNote>> = _imageList

    fun rememberImageList(imageList: List<ImageForNote>) {
        if (_imageList.value.isEmpty()) {
            _imageList.value = imageList
        }
    }
    fun rememberImage(noteId: Long, uri: String) {
        _imageList.value = _imageList.value + ImageForNote(null, noteId, uri)
    }

    fun deleteImage(listCount: Int) {
        _imageList.value = _imageList.value - _imageList.value[listCount]
    }

    fun updateNote(notes: Notes) {
        viewModelScope.launch {
            try {
                deleteNote.execute(noteId = notes.id!!)
                addNote.execute(note = notes)
            } catch (e: Exception) {
                Log.e("updateNote", "Error: $e")
            }
        }
    }
}
