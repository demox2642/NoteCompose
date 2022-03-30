package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.NoteDB.Companion.DB_VERSION
import com.example.data.database.tables.converters.PurchaseDateConverter
import com.example.data.database.tables.image.NoteImage
import com.example.data.database.tables.image.NoteImageDao
import com.example.data.database.tables.note.Note
import com.example.data.database.tables.note.NoteDao

@Database(
    entities = [
        NoteImage::class,
        Note::class
    ],
    version = DB_VERSION
)

@TypeConverters(PurchaseDateConverter::class)

abstract class NoteDB : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun noteImageDao(): NoteImageDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "NoteDB"
    }
}
