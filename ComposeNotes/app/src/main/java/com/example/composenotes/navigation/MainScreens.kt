package com.example.composenotes.navigation

sealed class MainScreens(
    val route: String
) {
    object NoteListScreen : MainScreens("note_list")
    object AddNoteScreen : MainScreens("add_note")
    object NoteDetail : MainScreens("detail_note")
    object RefactorNote : MainScreens("refactor_note")

    fun withArgs(vararg args: Long): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }

    fun withStringArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
