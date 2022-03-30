package com.example.composenotes

import android.app.Application
import com.example.composenotes.di.koinModules
import com.example.data.database.Database
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(context = this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(koinModules)
        }
    }
}
