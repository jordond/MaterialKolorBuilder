package com.materialkolor.builder.ui.home.section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction

@Composable
fun ExpandedContent(
    settings: Settings,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        CustomizeSection(
            settings = settings,
            modifier = Modifier.weight(1f),
        )

        PreviewSection(
            settings = settings,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun CompactContent(
    settings: Settings,
    selectedSection: HomeSection,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    when (selectedSection) {
        HomeSection.Customize -> CustomizeSection(
            settings = settings,
            modifier = modifier,
        )
        HomeSection.Preview -> PreviewSection(
            settings = settings,
            modifier = modifier,
        )
    }
}