package com.example.data.repository

import com.example.data.database.Database
import com.example.data.database.tables.image.NoteImage
import com.example.data.database.tables.note.Note
import com.example.domain.models.ImageForNote
import com.example.domain.models.Notes
import com.example.domain.repository.NoteRepository

class NotesRepositoryImpl : NoteRepository {

    private val noteDao = Database.instance.noteDao()
    private val noteImageDao = Database.instance.noteImageDao()

    override suspend fun getNoteList(): List<Notes> {
        return noteDao.getAllNote().map {
            Notes(
                id = it.note.id,
                name = it.note.name,
                note_text = it.note.note_text,
                add_date = it.note.add_date,
                refactor_date = it.note.refactor_date,
                images = it.noteImages?.map { it ->
                    ImageForNote(
                        id = it.id!!,
                        note_id = it.note_id,
                        image_linc = it.image_linc
                    )
                }
            )
        }
    }

    override suspend fun getNote(noteId: Long): Notes {
        return noteDao.getNote(noteId).let {
            Notes(
                id = it.note.id,
                name = it.note.name,
                note_text = it.note.note_text,
                add_date = it.note.add_date,
                refactor_date = it.note.refactor_date,
                images = it.noteImages?.map { it ->
                    ImageForNote(
                        id = it.id!!,
                        note_id = it.note_id,
                        image_linc = it.image_linc
                    )
                }
            )
        }
    }

    override suspend fun addNote(note: Notes) {
        noteDao.insertNote(
            Note(
                id = note.id,
                name = note.name,
                note_text = note.note_text,
                add_date = note.add_date,
                refactor_date = null
            )
        )
        if (!note.images.isNullOrEmpty()) {

//            noteImageDao.insertNoteImage(
//                note.images!!.map { it ->
//                    NoteImage(
//                        id = null,
//                        note_id = note.id,
//                        image_linc = it.image_linc
//                    )
//                }
//            )
        }
    }

    override suspend fun updateNote(note: Notes) {
        noteDao.updateNote(
            Note(
                id = note.id,
                name = note.name,
                note_text = note.note_text,
                add_date = note.add_date,
                refactor_date = null
            )
        )

        if (!note.images.isNullOrEmpty()) {
            for (i in 0 until note.images!!.size) {
                noteImageDao.updateNoteImage(
                    NoteImage(
                        id = note.images!![i].id,
                        note_id = note.images!![i].note_id,
                        image_linc = note.images!![i].image_linc
                    )
                )
            }
        } else {

            if (noteImageDao.getNoteImage(noteId = note.id!!).isNotEmpty()) {
                noteImageDao.deleteNoteImage(note.id!!)
            }
        }
    }

    override suspend fun deleteNote(note: Notes) {
        if (noteImageDao.getNoteImage(noteId = note.id!!).isNotEmpty()) {
            noteImageDao.deleteNoteImage(note.id!!)
        }
        noteDao.deleteNote(note.id!!)
    }

    override suspend fun deleteAllNote() {
        noteImageDao.deleteAllNoteImage()
        noteDao.deleteAllNote()
    }
}