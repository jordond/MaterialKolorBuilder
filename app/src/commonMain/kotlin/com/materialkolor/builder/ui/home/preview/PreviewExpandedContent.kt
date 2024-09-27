package com.materialkolor.builder.ui.home.preview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction
import com.materialkolor.builder.ui.home.HomeAction.CopyColor
import com.materialkolor.builder.ui.home.HomeAction.OpenColorPicker
import com.materialkolor.builder.ui.home.HomeAction.RandomColor
import com.materialkolor.builder.ui.home.HomeAction.SelectImage
import com.materialkolor.builder.ui.home.HomeAction.UpdateContrast
import com.materialkolor.builder.ui.home.HomeAction.UpdatePaletteStyle
import com.materialkolor.builder.ui.home.preview.customize.CustomizeSection
import com.materialkolor.builder.ui.home.preview.preview.PreviewSection

@Composable
fun PreviewExpandedContent(
    settings: Settings,
    processingImage: Boolean,
    dispatcher: Dispatcher<HomeAction>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        CustomizeSection(
            settings = settings,
            onSelectImage = dispatcher.rememberRelayOf(::SelectImage),
            onRandomColor = dispatcher.rememberRelay(RandomColor),
            openColorPicker = dispatcher.rememberRelayOf(::OpenColorPicker),
            onUpdatePaletteStyle = dispatcher.rememberRelayOf(::UpdatePaletteStyle),
            onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
            processingImage = processingImage,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.weight(0.5f),
        )

        PreviewSection(
            settings = settings,
            onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
            onCopyColor = dispatcher.rememberRelayOf(::CopyColor),
            modifier = Modifier.weight(1f),
            windowSizeClass = windowSizeClass,
        )
    }
}
