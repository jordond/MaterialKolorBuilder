package com.materialkolor.builder.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.materialkolor.builder.ui.home.page.HomeSection

@Composable
fun HomeBottomBar(
    selected: HomeSection,
    onSelected: (HomeSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        HomeSection.All.forEach { section ->
            val name = section.name()
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = section.icon(),
                        contentDescription = name,
                    )
                },
                label = { Text(name) },
                selected = section == selected,
                onClick = { onSelected(section) },
            )
        }
    }
}

@Composable
fun HomeNavRail(
    selected: HomeSection,
    onSelected: (HomeSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
    ) {
        HomeSection.All.forEach { section ->
            val name = section.name()
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = section.icon(),
                        contentDescription = name,
                    )
                },
                label = { Text(name) },
                selected = section == selected,
                onClick = { onSelected(section) },
            )
        }
    }
}

@Composable
private fun HomeSection.icon(): ImageVector = remember(this) {
    when (this) {
        HomeSection.Customize -> Icons.Default.Tune
        HomeSection.Themes -> Icons.Default.Contrast
        HomeSection.Preview -> Icons.Default.Smartphone
        HomeSection.Components -> Icons.Default.Preview
    }
}

@Composable
private fun HomeSection.name(): String = remember(this) {
    when (this) {
        HomeSection.Customize,
        HomeSection.Themes,
        HomeSection.Preview -> this.name
        HomeSection.Components -> "Gallery"
    }
}
