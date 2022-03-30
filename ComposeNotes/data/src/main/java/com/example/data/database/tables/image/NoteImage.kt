package com.example.data.database.tables.image

import androidx.room.*
import com.example.data.database.tables.note.Note
import com.example.data.database.tables.note.NoteContract

@Entity(
    tableName = NoteImageContract.TABLE_NAME,
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Note::class,
            parentColumns = [NoteContract.Colums.ID],
            childColumns = [NoteImageContract.Colums.NOTE_ID]
        )
    ]

)
data class NoteImage(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NoteImageContract.Colums.ID)
    val id: Long?,
    @ColumnInfo(name = NoteImageContract.Colums.NOTE_ID)
    val note_id: Long,
    @ColumnInfo(name = NoteImageContract.Colums.IMAGE_LINC)
    val image_linc: String
)
