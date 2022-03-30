package com.example.data.database.tables.note

import androidx.room.*
import com.example.data.database.tables.image.NoteImage
import com.example.data.database.tables.image.NoteImageContract
import java.time.Instant

@Entity(
    tableName = NoteContract.TABLE_NAME
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NoteContract.Colums.ID)
    val id: Long?,
    @ColumnInfo(name = NoteContract.Colums.NAME)
    val name: String,
    @ColumnInfo(name = NoteContract.Colums.NOTE_TEXT)
    val note_text: String,
    @ColumnInfo(name = NoteContract.Colums.ADD_DATE)
    var add_date: Long,
    @ColumnInfo(name = NoteContract.Colums.REFACTOR_DATE)
    var refactor_date: Long? = null
)

data class NoteWithImage(
    @Embedded
    val note: Note,
    @Relation(
        parentColumn = NoteContract.Colums.ID,
        entityColumn = NoteImageContract.Colums.NOTE_ID
    )
    var noteImages: List <NoteImage>? = null
)