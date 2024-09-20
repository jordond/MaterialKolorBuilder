package com.materialkolor.builder.core

import com.materialkolor.builder.settings.DarkModeProvider
import com.materialkolor.builder.settings.DefaultDarkModeProvider
import com.materialkolor.builder.settings.DefaultSettingsRepo
import com.materialkolor.builder.settings.SettingsRepo

object DI {

    val urlLauncher: UrlLauncher = DefaultUrlLauncher()

    val darkModeProvider: DarkModeProvider = DefaultDarkModeProvider()

    val settingsRepo: SettingsRepo by lazy {
        DefaultSettingsRepo(darkModeProvider)
    }
}