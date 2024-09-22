package com.materialkolor.builder.core

import com.materialkolor.builder.settings.DarkModeProvider
import com.materialkolor.builder.settings.DefaultDarkModeProvider
import com.materialkolor.builder.settings.DefaultSettingsRepo
import com.materialkolor.builder.settings.SettingsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object DI {

    val defaultScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    val urlLauncher: UrlLauncher = DefaultUrlLauncher()

    val darkModeProvider: DarkModeProvider = DefaultDarkModeProvider()

    val settingsRepo: SettingsRepo by lazy {
        DefaultSettingsRepo(darkModeProvider, defaultScope)
    }
}