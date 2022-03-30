package com.example.data.database

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: NoteDB
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            NoteDB::class.java,
            NoteDB.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}