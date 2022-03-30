package com.example.composenotes.ui.noteList

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.AddNote
import com.example.domain.usecase.GetNoteList

class NoteListViewModel(
    private val getNoteList: GetNoteList,

):ViewModel() {
}