package com.materialkolor.builder.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.materialkolor.builder.ui.home.page.HomeSection
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun HomeBottomBar(
    selected: HomeSection,
    onSelected: (HomeSection) -> Unit,
    modifier: Modifier = Modifier,
    items: PersistentList<HomeSection> = HomeSection.entries.toPersistentList(),
) {
    NavigationBar(
        modifier = modifier,
    ) {
        items.forEach { section ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = section.icon(),
                        contentDescription = section.name
                    )
                },
                label = { Text(section.name) },
                selected = section == selected,
                onClick = { onSelected(section) }
            )
        }
    }
}

@Composable
fun HomeNavRail(
    selected: HomeSection,
    onSelected: (HomeSection) -> Unit,
    modifier: Modifier = Modifier,
    items: PersistentList<HomeSection> = HomeSection.entries.toPersistentList(),
) {
    NavigationRail(
        modifier = modifier,
    ) {
        items.forEach { section ->
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = section.icon(),
                        contentDescription = section.name
                    )
                },
                label = { Text(section.name) },
                selected = section == selected,
                onClick = { onSelected(section) }
            )
        }
    }
}

private fun HomeSection.icon(): ImageVector = when (this) {
    HomeSection.Customize -> Icons.Default.Tune
    HomeSection.Preview -> Icons.Default.Preview
    HomeSection.Export -> Icons.Default.Download
}