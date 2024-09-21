package com.materialkolor.builder.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.core.exportSupported
import com.materialkolor.builder.core.rememberDebounceDispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction.*
import com.materialkolor.builder.ui.home.components.TopBarActions
import com.materialkolor.builder.ui.ktx.widthIsCompact
import com.materialkolor.builder.ui.ktx.windowSizeClass

@Composable
fun HomeScreen() {
    val model = viewModel { HomeModel() }
    val state by model.state.collectAsStateWithLifecycle()

    HomeScreenScaffold(
        settings = state.settings,
        dispatcher = rememberDebounceDispatcher { action ->
            when (action) {
                is ChangeContrast -> model.updateContrast(action.contrast)
                is SelectCustomImage -> {
                    // TODO: Implement image picker
                }
                is SelectImage -> model.selectImage(action.preset)
                is ToggleDarkMode -> model.toggleDarkMode()
                is LaunchUrl -> model.launchUrl(action.url)
                is Export -> {} // TODO: Implement export
            }
        }
    )
}