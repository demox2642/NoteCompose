package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class DeleteAllNote(private val noteRepository: NoteRepository) {
    suspend fun execute() {
        return noteRepository.deleteAllNote()
    }
}