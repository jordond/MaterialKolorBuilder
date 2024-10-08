package com.materialkolor.builder.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.core.UrlLink
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.theme.LocalUrlLauncher
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github

@Composable
fun TopBarActions(
    settings: Settings,
    onToggleDarkMode: () -> Unit,
    onAboutClicked: () -> Unit,
) {
    val urlLauncher = LocalUrlLauncher.current

    IconButton(onClick = onToggleDarkMode) {
        val icon =
            if (settings.isDarkMode) Icons.Outlined.LightMode
            else Icons.Outlined.DarkMode

        Icon(
            imageVector = icon,
            contentDescription = if (settings.isDarkMode) "Light mode" else "Dark mode",
        )
    }

    IconButton(onClick = onAboutClicked) {
        Icon(
            imageVector = Icons.Outlined.QuestionMark,
            contentDescription = "About MaterialKolor Builder",
        )
    }

    IconButton(onClick = { urlLauncher.launch(UrlLink.Github) }) {
        Icon(
            imageVector = FontAwesomeIcons.Brands.Github,
            contentDescription = "MaterialKolor source code on GitHub",
            modifier = Modifier.size(24.dp),
        )
    }
}
