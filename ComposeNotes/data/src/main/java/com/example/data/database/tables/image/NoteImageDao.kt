package com.example.data.database.tables.image

import androidx.room.*

@Dao
interface NoteImageDao {
    @Query("SELECT * FROM ${NoteImageContract.TABLE_NAME}")
    suspend fun getAllNoteImage(): List<NoteImage>

    @Query("SELECT * FROM ${NoteImageContract.TABLE_NAME} WHERE ${NoteImageContract.Colums.NOTE_ID} = :noteId ")
    suspend fun getNoteImage(noteId: Long): List<NoteImage>

    @Query("DELETE FROM ${NoteImageContract.TABLE_NAME} WHERE ${NoteImageContract.Colums.NOTE_ID} = :noteId ")
    suspend fun deleteNoteImage(noteId: Long)

    @Query("DELETE FROM ${NoteImageContract.TABLE_NAME}")
    suspend fun deleteAllNoteImage()

    @Insert
    suspend fun insertNoteImage(noteImage: List<NoteImage>)

    @Delete
    suspend fun deleteNoteImage(noteImage: NoteImage)
}
