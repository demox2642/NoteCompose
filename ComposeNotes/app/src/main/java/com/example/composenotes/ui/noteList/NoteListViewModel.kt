package com.example.composenotes.ui.noteList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Notes
import com.example.domain.usecase.DeleteAllNote
import com.example.domain.usecase.DeleteNote
import com.example.domain.usecase.GetNoteList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteListViewModel(
    private val getNoteList: GetNoteList,
    private val deleteNote: DeleteNote,
    private val deleteAllNote: DeleteAllNote
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Notes>>(emptyList())
    val noteList: StateFlow<List<Notes>> = _noteList

    private val _showMenu = MutableStateFlow(false)
    val showMenu: StateFlow<Boolean> = _showMenu

    init {
        getNoteList()
    }

    fun changeVisibleMenu() {
        _showMenu.value = _showMenu.value.not()
    }

    private fun getNoteList() {
        viewModelScope.launch {
            try {
                _noteList.value = getNoteList.execute()
            } catch (e: Exception) {
                Log.e("addNote", "Error: $e")
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            try {
                deleteNote.execute(noteId)
                getNoteList()
            } catch (e: Exception) {
                Log.e("deleteNote", "Error: $e")
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                deleteAllNote.execute()
                _showMenu.value = false
                _noteList.value = getNoteList.execute()
            } catch (e: Exception) {
                Log.e("deleteNote", "Error: $e")
            }
        }
    }
}
