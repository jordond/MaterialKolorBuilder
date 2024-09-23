package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.Contrast
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.components.ContrastSelector
import com.materialkolor.builder.ui.home.page.preview.theme.DarkThemeDisplay
import com.materialkolor.builder.ui.home.page.preview.theme.LightThemeDisplay
import com.materialkolor.builder.ui.ktx.widthIsExpanded
import com.materialkolor.builder.ui.ktx.windowSizeClass

@Composable
fun PreviewPage(
    settings: Settings,
    onUpdateContrast: (Contrast) -> Unit,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = windowSizeClass(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        if (windowSizeClass.widthIsExpanded()) {
            ContrastSelector(
                selected = settings.contrast,
                onUpdate = onUpdateContrast,
                modifier = Modifier.padding(vertical = 32.dp),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            LightThemeDisplay(settings)
            DarkThemeDisplay(settings)
        }
    }
}