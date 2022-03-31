package com.example.composenotes.utils

import android.util.Log
import com.example.domain.models.Notes
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

fun Long.toCalendarString(): String {

    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(this)
}

fun Notes.setAsJson(): String {
    val gson = Gson()
    return URLEncoder.encode(gson.toJson(this), StandardCharsets.UTF_8.toString())
}

fun String.toNotes(): Notes? {
    return try {

        if (this.isEmpty()) {
            null
        } else {
            val gson = Gson()
            gson.fromJson(this, Notes :: class.java)
        }
    } catch (e: Exception) {
        Log.e("StringToNotes", "ERROR : $e")
        null
    }
}
