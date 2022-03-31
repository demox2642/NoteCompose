package com.example.domain.repository

import com.example.domain.models.Notes

interface NoteRepository {
    suspend fun getNoteList(): List<Notes>
    suspend fun getNote(noteId: Long): Notes
    suspend fun addNote(note: Notes)
    suspend fun updateNote(note: Notes)
    suspend fun deleteNote(noteId: Long)
    suspend fun deleteAllNote()
}
