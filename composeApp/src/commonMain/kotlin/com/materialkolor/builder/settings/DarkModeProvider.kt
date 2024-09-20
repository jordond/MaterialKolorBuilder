package com.materialkolor.builder.settings

interface DarkModeProvider {

    val isDarkMode: Boolean

    fun initialize(isDarkMode: Boolean)
}

class DefaultDarkModeProvider : DarkModeProvider {

    override var isDarkMode: Boolean = false
        private set

    override fun initialize(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
    }
}