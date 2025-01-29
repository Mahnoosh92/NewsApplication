package com.mahnoosh.newsapplication

import android.app.Application
import com.mahnoosh.work.initializers.Sync

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Sync.initialize(this)
    }
}