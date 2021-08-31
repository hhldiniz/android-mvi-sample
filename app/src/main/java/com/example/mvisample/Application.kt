package com.example.mvisample

import android.app.Application
import androidx.room.Room
import com.example.mvisample.modules.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(modulesList)
        }
    }
}