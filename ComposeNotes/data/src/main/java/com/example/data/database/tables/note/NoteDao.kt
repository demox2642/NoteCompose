package com.example.data.database.tables.note

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${NoteContract.TABLE_NAME}")
    suspend fun getAllNote(): List<NoteWithImage>

    @Query("SELECT * FROM ${NoteContract.TABLE_NAME} WHERE ${NoteContract.Colums.ID} = :noteId")
    suspend fun getNote(noteId: Long): NoteWithImage

    @Insert
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM ${NoteContract.TABLE_NAME} WHERE ${NoteContract.Colums.ID} = :noteId")
    suspend fun deleteNote(noteId: Long)

    @Query("DELETE FROM ${NoteContract.TABLE_NAME}")
    suspend fun deleteAllNote()

    @Update
    fun updateNote(note: Note)
}
