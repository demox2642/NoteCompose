package com.example.composenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenotes.ui.addnote.AddNoteScreen
import com.example.composenotes.ui.noteList.NoteListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreens.NoteListScreen.route
    ) {
        composable(route = MainScreens.NoteListScreen.route) { NoteListScreen(navController) }
        composable(route = MainScreens.AddNoteScreen.route) { AddNoteScreen(navController) }
//        composable(
//            route = MainScreens.ImageDetail.route + "/{linc}",
//            arguments = listOf(
//                navArgument("linc") {
//                    type = NavType.IntType
//                }
//            )
//        ) { entry ->
//
        //
        //
        //          ImageDetail(imageIndex = entry.arguments?.getInt("linc")!!)
    }
}
