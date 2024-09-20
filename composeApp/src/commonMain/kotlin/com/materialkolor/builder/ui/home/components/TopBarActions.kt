package com.materialkolor.builder.ui.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.core.UrlLink
import com.materialkolor.builder.settings.model.Settings
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github

@Composable
@Suppress("UnusedReceiverParameter")
fun RowScope.TopBarActions(
    settings: Settings,
    onToggleDarkMode: () -> Unit,
    onLaunchUrl: (url: UrlLink) -> Unit,
) {
    IconButton(onClick = onToggleDarkMode) {
        val icon =
            if (settings.isDarkMode) Icons.Outlined.LightMode
            else Icons.Outlined.DarkMode

        Icon(
            imageVector = icon,
            contentDescription = if (settings.isDarkMode) "Light mode" else "Dark mode"
        )
    }

    IconButton(onClick = { onLaunchUrl(UrlLink.Source) }) {
        Icon(
            imageVector = Icons.Outlined.Code,
            contentDescription = "MaterialKolor Builder Source"
        )
    }

    IconButton(onClick = { onLaunchUrl(UrlLink.Github) }) {
        Icon(
            imageVector = FontAwesomeIcons.Brands.Github,
            contentDescription = "MaterialKolor source code on GitHub",
            modifier = Modifier.size(24.dp),
        )
    }
}