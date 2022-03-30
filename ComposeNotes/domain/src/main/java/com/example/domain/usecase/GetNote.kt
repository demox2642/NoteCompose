package com.example.domain.usecase

import com.example.domain.models.Notes
import com.example.domain.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {
    suspend fun execute(noteId: Long): Notes {
        return noteRepository.getNote(noteId)
    }
}