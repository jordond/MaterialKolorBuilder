package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.Contrast
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.components.ContrastSelector
import com.materialkolor.builder.ui.home.page.device.DeviceSection
import com.materialkolor.builder.ui.home.page.gallery.GallerySection
import com.materialkolor.builder.ui.home.page.palette.PaletteSection
import com.materialkolor.builder.ui.home.page.theme.ThemeSection
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
    Box(
        modifier = modifier
            .padding(horizontal = windowSizeClass.sidePadding())
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            PreviewSectionContainer(title = "Preview") {
                DeviceSection()
            }

            GallerySection()

            PreviewSectionContainer(title = "Theme") {
                ThemeSection(
                    settings = settings,
                    onCopyColor = onCopyColor,
                )
            }

            PreviewSectionContainer(title = "Palettes") {
                PaletteSection()
            }

            Spacer(modifier = Modifier.height(200.dp))
        }

        if (windowSizeClass.widthIsExpanded()) {
            ContrastSelector(
                selected = settings.contrast,
                onUpdate = onUpdateContrast,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
            )
        }
    }
}
