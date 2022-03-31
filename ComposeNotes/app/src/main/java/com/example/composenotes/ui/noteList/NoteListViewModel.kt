package com.example.composenotes.ui.noteList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.tables.note.Note
import com.example.domain.models.Notes
import com.example.domain.usecase.DeleteNote
import com.example.domain.usecase.GetNoteList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteListViewModel(
    private val getNoteList: GetNoteList,
    private val deleteNote: DeleteNote
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Notes>>(emptyList())
    val noteList: StateFlow<List<Notes>> = _noteList

    init {
        getNoteList()
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
}
