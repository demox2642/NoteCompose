package com.example.composenotes.di

import com.example.composenotes.ui.addnote.AddNoteViewModel
import com.example.composenotes.ui.noteList.NoteListViewModel
import com.example.composenotes.ui.notedetail.NoteDetailViewModel
import com.example.composenotes.ui.refactornote.RefactorNoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { NoteListViewModel(get(), get(), get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { NoteDetailViewModel(get()) }
    viewModel { RefactorNoteViewModel(get(), get()) }
}
