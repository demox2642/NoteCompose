package com.example.composenotes.di

import com.example.composenotes.ui.addnote.AddNoteViewModel
import com.example.composenotes.ui.noteList.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { NoteListViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
}
