package com.example.domain.usecase

import com.example.domain.models.Notes
import com.example.domain.repository.NoteRepository

class GetNoteList(private val noteRepository: NoteRepository) {
    suspend fun execute(): List<Notes> {
        return noteRepository.getNoteList()
    }
}