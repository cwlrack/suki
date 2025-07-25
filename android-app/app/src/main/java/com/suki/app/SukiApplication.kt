package com.suki.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SukiApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Hilt will handle dependency injection setup
        // Any other app-wide initialization can go here
    }
}
