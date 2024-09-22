package com.materialkolor.builder.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.core.exportSupported
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.about.AboutInfo
import com.materialkolor.builder.ui.home.HomeAction.Export
import com.materialkolor.builder.ui.home.HomeAction.ToggleDarkMode
import com.materialkolor.builder.ui.home.components.HomeBottomBar
import com.materialkolor.builder.ui.home.components.HomeNavRail
import com.materialkolor.builder.ui.home.components.TopBarActions
import com.materialkolor.builder.ui.home.page.HomeSection
import com.materialkolor.builder.ui.ktx.widthIsCompact
import com.materialkolor.builder.ui.ktx.widthIsExpanded
import com.materialkolor.builder.ui.ktx.windowSizeClass

@Composable
fun HomeScreenScaffold(
    settings: Settings,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
) {
    var aboutDialogVisible by remember { mutableStateOf(false) }
    var selectedSection by remember { mutableStateOf(HomeSection.Customize) }

    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val windowSizeClass = windowSizeClass()

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Palette,
                            contentDescription = "MaterialKolor Builder",
                            tint = MaterialTheme.colorScheme.primary,
                        )

                        val text = if (windowSizeClass.widthIsCompact()) "MKB"
                        else "MaterialKolor Builder"

                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Thin,
                            ),
                        )
                    }
                },
                scrollBehavior = behavior,
                actions = {
                    TopBarActions(
                        settings = settings,
                        onToggleDarkMode = dispatcher.relay(ToggleDarkMode),
                        onAboutClicked = { aboutDialogVisible = true },
                    )
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(windowSizeClass.widthIsCompact()) {
                HomeBottomBar(
                    selected = selectedSection,
                    onSelected = { selectedSection = it },
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(exportSupported && windowSizeClass.widthIsExpanded()) {
                ExtendedFloatingActionButton(
                    onClick = dispatcher.relay(Export),
                    text = {
                        Text(text = "Export")
                    },
                    icon = {
                        Icon(Icons.Default.Download, contentDescription = "Export")
                    },
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Expanded -> {
                    ExpandedContent(
                        settings = settings,
                        dispatcher = dispatcher,
                    )
                }
                WindowWidthSizeClass.Medium -> {
                    Row {
                        HomeNavRail(
                            selected = selectedSection,
                            onSelected = { selectedSection = it },
                        )
                        CompactContent(
                            settings = settings,
                            selectedSection = selectedSection,
                            dispatcher = dispatcher,
                        )
                    }
                }
                WindowWidthSizeClass.Compact -> {
                    CompactContent(
                        settings = settings,
                        selectedSection = selectedSection,
                        dispatcher = dispatcher,
                    )
                }
            }
        }

        AboutInfo(
            visible = aboutDialogVisible,
            onDismiss = { aboutDialogVisible = false },
            windowSizeClass = windowSizeClass,
        )
    }
}