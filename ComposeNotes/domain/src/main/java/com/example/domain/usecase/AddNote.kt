package com.example.domain.usecase

import com.example.domain.models.Notes
import com.example.domain.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend fun execute(note: Notes) {
        return noteRepository.addNote(note)
    }
}