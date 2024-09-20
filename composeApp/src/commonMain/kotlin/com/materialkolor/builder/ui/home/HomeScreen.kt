package com.materialkolor.builder.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.core.rememberDebounceDispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction.LaunchUrl
import com.materialkolor.builder.ui.home.components.TopBarActions

@Composable
fun HomeScreen() {
    val model = viewModel { HomeModel() }
    val state by model.state.collectAsStateWithLifecycle()

    HomeScreenScaffold(
        settings = state.settings,
        dispatcher = rememberDebounceDispatcher { action ->
            when (action) {
                is HomeAction.ChangeContrast -> model.updateContrast(action.contrast)
                is HomeAction.SelectCustomImage -> {
                    // TODO: Implement image picker
                }
                is HomeAction.SelectImage -> model.selectImage(action.preset)
                is HomeAction.ToggleDarkMode -> model.toggleDarkMode()
                is LaunchUrl -> model.launchUrl(action.url)
            }
        }
    )
}

@Composable
fun HomeScreenScaffold(
    settings: Settings,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MaterialKolor Builder")
                },
                actions = {
                    TopBarActions(
                        settings = settings,
                        onToggleDarkMode = dispatcher.relay(HomeAction.ToggleDarkMode),
                        onLaunchUrl = dispatcher.relayOf(::LaunchUrl),
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {

        }
    }
}