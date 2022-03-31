package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class DeleteNote(private val noteRepository: NoteRepository) {
    suspend fun execute(noteId: Long) {
        return noteRepository.deleteNote(noteId)
    }
}
