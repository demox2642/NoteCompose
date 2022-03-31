package com.example.composenotes.utils

import java.text.SimpleDateFormat

inline fun Long.toCalendarString(): String {

    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(this)
}
