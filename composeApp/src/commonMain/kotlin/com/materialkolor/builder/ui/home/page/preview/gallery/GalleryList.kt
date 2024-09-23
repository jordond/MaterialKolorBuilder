package com.materialkolor.builder.ui.home.page.preview.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.ActionGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.NavigationGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.TextGallerySection

@Composable
fun GalleryList(
    modifier: Modifier = Modifier,
) {
    var actionExpanded by remember { mutableStateOf(true) }
    var textExpanded by remember { mutableStateOf(true) }
    var navigationExpanded by remember { mutableStateOf(true) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        ActionGallerySection(
            expanded = actionExpanded,
            toggle = { actionExpanded = it },
        )

        TextGallerySection(
            expanded = textExpanded,
            toggle = { textExpanded = it },
            width = 500.dp,
        )

        NavigationGallerySection(
            expanded = navigationExpanded,
            toggle = { navigationExpanded = it },
        )
    }
}