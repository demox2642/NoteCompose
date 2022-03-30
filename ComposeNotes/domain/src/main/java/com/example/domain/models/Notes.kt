package com.example.domain.models

data class Notes(
    val id: Long? = null,
    val name: String,
    val note_text: String,
    var add_date: Long,
    var refactor_date: Long? = null,
    val images: List<ImageForNote>? = null
)
