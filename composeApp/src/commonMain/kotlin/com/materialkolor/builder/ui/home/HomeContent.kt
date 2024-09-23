package com.materialkolor.builder.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction.CopyColor
import com.materialkolor.builder.ui.home.HomeAction.OpenColorPicker
import com.materialkolor.builder.ui.home.HomeAction.RandomColor
import com.materialkolor.builder.ui.home.HomeAction.SelectPresetImage
import com.materialkolor.builder.ui.home.HomeAction.UpdateContrast
import com.materialkolor.builder.ui.home.HomeAction.UpdatePaletteStyle
import com.materialkolor.builder.ui.home.page.HomeSection
import com.materialkolor.builder.ui.home.page.customize.CustomizePage
import com.materialkolor.builder.ui.home.page.export.ExportPage
import com.materialkolor.builder.ui.home.page.preview.PreviewPage

@Composable
fun ExpandedContent(
    settings: Settings,
    dispatcher: Dispatcher<HomeAction>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        CustomizePage(
            settings = settings,
            onPresetSelected = dispatcher.rememberRelayOf(::SelectPresetImage),
            onRandomColor = dispatcher.rememberRelay(RandomColor),
            openColorPicker = dispatcher.rememberRelayOf(::OpenColorPicker),
            onUpdatePaletteStyle = dispatcher.rememberRelayOf(::UpdatePaletteStyle),
            onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
            windowSizeClass = windowSizeClass,
            modifier = Modifier.weight(0.5f)
        )

        PreviewPage(
            settings = settings,
            onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
            onCopyColor = dispatcher.rememberRelayOf(::CopyColor),
            modifier = Modifier.weight(1f),
            windowSizeClass = windowSizeClass,
        )
    }
}

@Composable
fun CompactContent(
    settings: Settings,
    selectedSection: HomeSection,
    dispatcher: Dispatcher<HomeAction>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    Crossfade(selectedSection) { section ->
        when (section) {
            HomeSection.Customize -> CustomizePage(
                settings = settings,
                onPresetSelected = dispatcher.rememberRelayOf(::SelectPresetImage),
                onRandomColor = dispatcher.rememberRelay(RandomColor),
                openColorPicker = dispatcher.rememberRelayOf(::OpenColorPicker),
                onUpdatePaletteStyle = dispatcher.rememberRelayOf(::UpdatePaletteStyle),
                onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
                windowSizeClass = windowSizeClass,
                modifier = modifier,
            )
            HomeSection.Preview -> PreviewPage(
                settings = settings,
                onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
                onCopyColor = dispatcher.rememberRelayOf(::CopyColor),
                modifier = modifier,
                windowSizeClass = windowSizeClass,
            )
            HomeSection.Export -> ExportPage(
                settings = settings,
                modifier = modifier,
            )
        }
    }
}