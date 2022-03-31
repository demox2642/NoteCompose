package com.example.composenotes.di

import com.example.composenotes.ui.addnote.AddNoteViewModel
import com.example.composenotes.ui.noteList.NoteListViewModel
import com.example.composenotes.ui.notedetail.NoteDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { NoteListViewModel(get(), get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { NoteDetailViewModel(get()) }
}
