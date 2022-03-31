package com.example.composenotes.ui.notedetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ImageForNote
import com.example.domain.models.Notes
import com.example.domain.usecase.GetNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteDetailViewModel(
    private val getNote: GetNote
) : ViewModel() {
    private val _note = MutableStateFlow<Notes?>(null)
    val note: StateFlow<Notes?> = _note

    private val _imageList = MutableStateFlow<List<ImageForNote>? >(emptyList())
    val imageList: StateFlow<List<ImageForNote>? > = _imageList

    fun getNote(noteId: Long) {
        viewModelScope.launch {
            try {
                _note.value = getNote.execute(noteId).also {
                    _imageList.value = it.images
                }
            } catch (e: Exception) {
                Log.e("getNote", "ERROR : $e")
            }
        }
    }
}
