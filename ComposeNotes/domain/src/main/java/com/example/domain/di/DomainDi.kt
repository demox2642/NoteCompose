package com.example.domain.di

import com.example.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    single { AddNote(get()) }
    single { DeleteAllNote(get()) }
    single { DeleteNote(get()) }
    single { GetNote(get()) }
    single { GetNoteList(get()) }
}
