package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.Contrast
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.components.ContrastSelector
import com.materialkolor.builder.ui.home.page.preview.gallery.GalleryList
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplay
import com.materialkolor.builder.ui.ktx.sidePadding
import com.materialkolor.builder.ui.ktx.widthIsExpanded
import com.materialkolor.builder.ui.ktx.windowSizeClass

@Composable
fun PreviewPage(
    settings: Settings,
    onUpdateContrast: (Contrast) -> Unit,
    modifier: Modifier = Modifier,
    onCopyColor: (String, Color) -> Unit = { _, _ -> },
    windowSizeClass: WindowSizeClass = windowSizeClass(),
) {
    var themeExpanded by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = windowSizeClass.sidePadding())
            .verticalScroll(rememberScrollState()),
    ) {
        if (windowSizeClass.widthIsExpanded()) {
            ContrastSelector(
                selected = settings.contrast,
                onUpdate = onUpdateContrast,
                modifier = Modifier.padding(vertical = 32.dp),
            )
        }

        GalleryList()

        PreviewSection(
            title = "Theme",
            expanded = themeExpanded,
            toggle = { themeExpanded = it },
        ) {
            ThemeDisplay(
                settings = settings,
                onCopyColor = onCopyColor,
            )
        }

        Spacer(modifier = Modifier.height(200.dp))
    }
}