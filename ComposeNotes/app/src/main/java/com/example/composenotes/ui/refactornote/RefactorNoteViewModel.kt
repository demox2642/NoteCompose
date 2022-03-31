package com.example.composenotes.ui.refactornote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenotes.navigation.MainScreens
import com.example.domain.models.ImageForNote
import com.example.domain.models.Notes
import com.example.domain.usecase.GetNote
import com.example.domain.usecase.UpdeteNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class RefactorNoteViewModel(
    private val getNote: GetNote,
    private val updeteNote: UpdeteNote
) : ViewModel() {
    private val _note = MutableStateFlow<Notes?>(null)
    val note: StateFlow<Notes?> = _note

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

    private val _imageList = MutableStateFlow<List<String> >(emptyList())
    val imageList: StateFlow<List<String>> = _imageList

    fun getNote(noteId: Long) {
        Log.e("getNote", "start")
        viewModelScope.launch {
            try {
                _note.value = getNote.execute(noteId)
            } catch (e: Exception) {
                Log.e("getNote", "ERROR : $e")
            }
        }
    }

    fun rememberImageList(imageList: List<ImageForNote>) {
        if (_imageList.value.isEmpty()) {
            _imageList.value = imageList.map {
                it.image_linc
            }
        }
    }
    fun rememberImage(uri: String) {
        Log.e("AddNoteViewModel", " uri = $uri _imageList = ${_imageList.value}")
        _imageList.value = _imageList.value + uri
        Log.e("AddNoteViewModel", " uri = $uri _imageList = ${_imageList.value}")
    }

    fun deleteImage(listCount: Int) {
        Log.e("deleteImage", " listCount = $listCount  _imageList ={${_imageList.value}}")
        _imageList.value = _imageList.value - _imageList.value[listCount]
    }

    fun goToList() {
        MainScreens.NoteListScreen.route
    }
}
