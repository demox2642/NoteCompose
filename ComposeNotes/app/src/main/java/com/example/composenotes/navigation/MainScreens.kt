package com.example.composenotes.navigation

sealed class MainScreens(
    val route: String
) {
    object NoteListScreen : MainScreens("note_list")
    object AddNoteScreen : MainScreens("add_note")

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
