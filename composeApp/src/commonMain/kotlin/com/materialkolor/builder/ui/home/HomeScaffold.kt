package com.materialkolor.builder.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.core.exportSupported
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.HomeAction.Export
import com.materialkolor.builder.ui.home.HomeAction.LaunchUrl
import com.materialkolor.builder.ui.home.HomeAction.ToggleDarkMode
import com.materialkolor.builder.ui.home.components.HomeBottomBar
import com.materialkolor.builder.ui.home.components.TopBarActions
import com.materialkolor.builder.ui.home.section.CompactContent
import com.materialkolor.builder.ui.home.section.ExpandedContent
import com.materialkolor.builder.ui.home.section.HomeSection
import com.materialkolor.builder.ui.ktx.showBottomBar
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
                    if (windowSizeClass.widthIsCompact()) {
                        Text(text = "MKB")
                    } else {
                        Text(text = "MaterialKolor Builder")
                    }
                },
                scrollBehavior = behavior,
                actions = {
                    TopBarActions(
                        settings = settings,
                        onToggleDarkMode = dispatcher.relay(ToggleDarkMode),
                        onLaunchUrl = dispatcher.relayOf(::LaunchUrl),
                        onAboutClicked = { aboutDialogVisible = true },
                    )
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(windowSizeClass.showBottomBar()) {
                HomeBottomBar(
                    selected = selectedSection,
                    onSelected = { selectedSection = it },
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(exportSupported) {
                IconButton(onClick = dispatcher.relay(Export)) {
                    Icon(Icons.Default.Download, contentDescription = "Export")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            if (windowSizeClass.widthIsExpanded()) {
                ExpandedContent(
                    settings = settings,
                    dispatcher = dispatcher,
                )
            } else {
                CompactContent(
                    settings = settings,
                    selectedSection = selectedSection,
                    dispatcher = dispatcher,
                )
            }
        }
    }
}