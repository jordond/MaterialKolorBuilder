package com.materialkolor.builder.ui.home.page.customize

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import com.materialkolor.builder.settings.model.ImagePresets
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.ColorType
import com.materialkolor.builder.ui.home.page.customize.colors.CoreColorsSection
import com.materialkolor.builder.ui.home.page.customize.seed.SeedColorSection
import com.materialkolor.builder.ui.home.page.customize.style.PaletteStyleSection
import kotlinx.collections.immutable.PersistentList

@Composable
fun CustomizePage(
    settings: Settings,
    modifier: Modifier = Modifier,
    onPresetSelected: (SeedImage.Resource) -> Unit,
    openColorPicker: (ColorType) -> Unit,
    onRandomColor: () -> Unit,
    onUpdatePaletteStyle: (PaletteStyle) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    imagePresets: PersistentList<SeedImage.Resource> = ImagePresets.all,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Generate your own Material 3 color scheme, powered by MaterialKolor.",
        )

        SeedColorSection(
            settings = settings,
            onPresetSelected = onPresetSelected,
            openColorPicker = { openColorPicker(ColorType.Seed) },
            onRandomColor = onRandomColor,
            imagePresets = imagePresets,
        )

        PaletteStyleSection(
            selected = settings.style,
            onUpdate = onUpdatePaletteStyle,
        )

        CoreColorsSection(
            onClickColor = openColorPicker,
        )

        Spacer(modifier = Modifier.height(200.dp))
    }
}