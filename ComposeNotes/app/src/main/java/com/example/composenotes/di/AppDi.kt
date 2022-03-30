package com.example.composenotes.di

import com.example.data.di.dataModule
import com.example.domain.di.domainModule

val koinModules = listOf(
    presentationModule,
    domainModule,
    dataModule
)
