package com.materialkolor.builder.ui

import com.materialkolor.builder.di.Di
import com.materialkolor.builder.settings.SettingsRepo
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.ktx.StateViewModel

class AppModel(
    settingsRepo: SettingsRepo = Di.settingsRepo(),
) : StateViewModel<AppModel.State>(State()) {

    init {
        settingsRepo.settings.collectToState { state, value ->
            state.copy(settings = value)
        }
    }

    data class State(
        val settings: Settings? = null,
    )
}