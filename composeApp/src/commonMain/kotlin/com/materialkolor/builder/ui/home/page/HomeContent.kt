package com.materialkolor.builder.ui.home.page

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction
import com.materialkolor.builder.ui.home.HomeAction.LaunchUrl
import com.materialkolor.builder.ui.home.HomeAction.SelectPresetImage
import com.materialkolor.builder.ui.home.page.customize.CustomizePage

@Composable
fun ExpandedContent(
    settings: Settings,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        CustomizePage(
            settings = settings,
            onLaunchUrl = dispatcher.rememberRelayOf(::LaunchUrl),
            onPresetSelected = dispatcher.rememberRelayOf(::SelectPresetImage),
            onRandomColor = dispatcher.rememberRelay(HomeAction.RandomColor),
            openColorPicker = dispatcher.rememberRelay(HomeAction.OpenColorPicker),
            modifier = Modifier.weight(0.5f)
        )

        PreviewPage(
            settings = settings,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun CompactContent(
    settings: Settings,
    selectedSection: HomeSection,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    Crossfade(selectedSection) { section ->
        when (section) {
            HomeSection.Customize -> CustomizePage(
                settings = settings,
                onLaunchUrl = dispatcher.rememberRelayOf(::LaunchUrl),
                onPresetSelected = dispatcher.rememberRelayOf(::SelectPresetImage),
                onRandomColor = dispatcher.rememberRelay(HomeAction.RandomColor),
                openColorPicker = dispatcher.rememberRelay(HomeAction.OpenColorPicker),
                modifier = modifier,
            )
            HomeSection.Preview -> PreviewPage(
                settings = settings,
                modifier = modifier,
            )
            HomeSection.Export -> ExportPage(
                settings = settings,
                modifier = modifier,
            )
        }
    }
}