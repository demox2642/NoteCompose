package com.example.data.di

import com.example.data.repository.NotesRepositoryImpl
import com.example.domain.repository.NoteRepository
import org.koin.dsl.module

val dataModule = module {
    single <NoteRepository> { NotesRepositoryImpl() }
}
