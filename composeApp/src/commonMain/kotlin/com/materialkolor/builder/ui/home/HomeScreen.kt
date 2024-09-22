package com.materialkolor.builder.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.builder.core.rememberDebounceDispatcher
import com.materialkolor.builder.ui.home.HomeAction.Export
import com.materialkolor.builder.ui.home.HomeAction.OpenColorPicker
import com.materialkolor.builder.ui.home.HomeAction.RandomColor
import com.materialkolor.builder.ui.home.HomeAction.Reset
import com.materialkolor.builder.ui.home.HomeAction.SelectCustomImage
import com.materialkolor.builder.ui.home.HomeAction.SelectPresetImage
import com.materialkolor.builder.ui.home.HomeAction.ToggleDarkMode
import com.materialkolor.builder.ui.home.HomeAction.UpdateContrast
import com.materialkolor.builder.ui.home.HomeAction.UpdatePaletteStyle

@Composable
fun HomeScreen() {
    val model = viewModel { HomeModel() }
    val state by model.state.collectAsStateWithLifecycle()

    HomeScreenScaffold(
        settings = state.settings,
        dispatcher = rememberDebounceDispatcher { action ->
            when (action) {
                is UpdateContrast -> model.updateContrast(action.contrast)
                is UpdatePaletteStyle -> model.updatePaletteStyle(action.style)
                is SelectCustomImage -> {
                    // TODO: Implement image picker
                }
                is SelectPresetImage -> model.selectImage(action.preset)
                is ToggleDarkMode -> model.toggleDarkMode()
                is Export -> {} // TODO: Implement export
                is OpenColorPicker -> {} // TODO: Implement color picker
                is RandomColor -> model.randomColor()
                is Reset -> model.reset()
            }
        }
    )
}