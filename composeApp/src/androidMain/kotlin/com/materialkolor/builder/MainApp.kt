package com.materialkolor.builder

import android.app.Application
import android.content.Context

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MainApp? = null

        fun context(): Context {
            return instance?.applicationContext ?: error("MainApp is not initialized")
        }
    }
}