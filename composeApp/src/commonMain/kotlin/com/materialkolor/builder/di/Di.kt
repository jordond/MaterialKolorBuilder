package com.materialkolor.builder.di

import com.materialkolor.builder.settings.DarkModeProvider
import com.materialkolor.builder.settings.DefaultDarkModeProvider
import com.materialkolor.builder.settings.DefaultSettingsRepo
import com.materialkolor.builder.settings.SettingsRepo

object Di {

    val darkModeProvider: DarkModeProvider = DefaultDarkModeProvider()

    fun settingsRepo(): SettingsRepo = DefaultSettingsRepo(darkModeProvider)
}