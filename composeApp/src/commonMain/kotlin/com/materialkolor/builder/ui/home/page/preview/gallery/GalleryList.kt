package com.materialkolor.builder.ui.home.page.preview.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.ActionGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.CommunicationGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.ContainmentGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.NavigationGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.SelectionGallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.sections.TextGallerySection

@Composable
fun GalleryList(
    modifier: Modifier = Modifier,
    defaultExpanded: Boolean = true,
) {
    var actionExpanded by remember { mutableStateOf(defaultExpanded) }
    var textExpanded by remember { mutableStateOf(defaultExpanded) }
    var communicationExpanded by remember { mutableStateOf(defaultExpanded) }
    var containmentExpanded by remember { mutableStateOf(defaultExpanded) }
    var selectionExpanded by remember { mutableStateOf(defaultExpanded) }
    var navigationExpanded by remember { mutableStateOf(defaultExpanded) }

    val anyExpanded = remember(
        actionExpanded,
        textExpanded,
        communicationExpanded,
        containmentExpanded,
        selectionExpanded,
        navigationExpanded,
    ) {
        listOf(
            actionExpanded,
            textExpanded,
            communicationExpanded,
            containmentExpanded,
            selectionExpanded,
            navigationExpanded,
        ).any { it }
    }

    fun toggleAll(expanded: Boolean) {
        actionExpanded = expanded
        textExpanded = expanded
        communicationExpanded = expanded
        containmentExpanded = expanded
        selectionExpanded = expanded
        navigationExpanded = expanded
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = { toggleAll(!anyExpanded) })
                .padding(16.dp),
        ) {
            Text(
                text = "Component Gallery",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Thin,
                ),
            )

            val icon =
                if (anyExpanded) Icons.Default.Visibility
                else Icons.Default.VisibilityOff

            val text = if (anyExpanded) "Collapse all" else "Expand all"
            Icon(imageVector = icon, contentDescription = text)
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 3,
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

            CommunicationGallerySection(
                expanded = communicationExpanded,
                toggle = { communicationExpanded = it },
            )

            ContainmentGallerySection(
                expanded = containmentExpanded,
                toggle = { containmentExpanded = it },
            )

            SelectionGallerySection(
                expanded = selectionExpanded,
                toggle = { selectionExpanded = it },
                width = 500.dp,
            )

            NavigationGallerySection(
                expanded = navigationExpanded,
                toggle = { navigationExpanded = it },
            )
        }
    }
}