package com.example.composenotes.ui.addnote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Notes
import com.example.domain.usecase.AddNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AddNoteViewModel(
    private val addNote: AddNote
) : ViewModel() {
    private val _imageList = MutableStateFlow<List<String>>(emptyList())
    val imageList: StateFlow<List<String>> = _imageList

    private val _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog

    fun changeVisibleAlertDialog() {
        _showAlertDialog.value = _showAlertDialog.value.not()
    }

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    fun changeVisibleErrorDialog() {
        _showErrorDialog.value = _showErrorDialog.value.not()
    }

    fun addNote(
        name: String,
        note_text: String
    ) {
        val calendar = Calendar.getInstance()
        viewModelScope.launch {
            addNote.execute(
                Notes(
                    name = name,
                    note_text = note_text,
                    add_date = calendar.timeInMillis
                )
            )
        }
    }

    fun rememberImage(uri: String) {
        Log.e("AddNoteViewModel", " uri = $uri")
        _imageList.value = _imageList.value + uri
    }
}
