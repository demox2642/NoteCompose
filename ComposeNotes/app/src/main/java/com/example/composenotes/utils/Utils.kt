package com.example.composenotes.utils

import java.util.*

inline fun Long.toCalendarString(): String{
    val c = Calendar.getInstance()
    c.timeInMillis = this
    return "${c.get(Calendar.DAY_OF_MONTH)}.${c.get(Calendar.MONTH)}.${c.get(Calendar.YEAR)}"
}
