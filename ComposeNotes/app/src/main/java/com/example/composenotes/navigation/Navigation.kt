package com.example.composenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenotes.ui.addnote.AddNoteScreen
import com.example.composenotes.ui.noteList.NoteListScreen
import com.example.composenotes.ui.notedetail.NoteDetail
import com.example.composenotes.ui.refactornote.RefactorNote

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreens.NoteListScreen.route
    ) {
        composable(route = MainScreens.NoteListScreen.route) { NoteListScreen(navController) }
        composable(route = MainScreens.AddNoteScreen.route) { AddNoteScreen(navController) }
        composable(
            route = MainScreens.NoteDetail.route + "/{linc}",
            arguments = listOf(
                navArgument("linc") {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            NoteDetail(noteId = entry.arguments?.getLong("linc")!!, navController = navController)
        }

        composable(
            route = MainScreens.RefactorNote.route + "/{linc}",
            arguments = listOf(
                navArgument("linc") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            RefactorNote(noteArg = entry.arguments?.getString("linc")!!, navController = navController)
        }
    }
}
