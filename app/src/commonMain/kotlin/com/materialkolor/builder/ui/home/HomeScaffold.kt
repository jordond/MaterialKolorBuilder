package com.materialkolor.builder.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.core.exportSupported
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.about.AboutInfo
import com.materialkolor.builder.ui.components.AppSnackbarHost
import com.materialkolor.builder.ui.components.AppTopBar
import com.materialkolor.builder.ui.components.ColorPickerDialog
import com.materialkolor.builder.ui.components.ColorPickerState
import com.materialkolor.builder.ui.home.HomeAction.Export
import com.materialkolor.builder.ui.home.HomeAction.Share
import com.materialkolor.builder.ui.home.HomeAction.ToggleDarkMode
import com.materialkolor.builder.ui.home.HomeAction.UpdateColor
import com.materialkolor.builder.ui.home.components.HomeBottomBar
import com.materialkolor.builder.ui.home.components.HomeNavRail
import com.materialkolor.builder.ui.home.page.CompactContent
import com.materialkolor.builder.ui.home.page.ExpandedContent
import com.materialkolor.builder.ui.home.page.HomeSection
import com.materialkolor.builder.ui.ktx.widthIsCompact
import com.materialkolor.builder.ui.ktx.widthIsExpanded
import com.materialkolor.builder.ui.ktx.windowSizeClass

@Composable
fun HomeScreenScaffold(
    settings: Settings,
    colorPickerState: ColorPickerState?,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
    initialSection: HomeSection? = null,
    snackbarState: SnackbarHostState = remember { SnackbarHostState() },
    processingImage: Boolean = false,
    windowSizeClass: WindowSizeClass = windowSizeClass(),
) {
    var aboutDialogVisible by remember { mutableStateOf(false) }
    var selectedSection by remember { mutableStateOf(initialSection ?: HomeSection.Customize) }

    Scaffold(
        modifier = modifier,
        snackbarHost = { AppSnackbarHost(snackbarState) },
        topBar = {
            AppTopBar(
                settings = settings,
                toggleDarkMode = dispatcher.relay(ToggleDarkMode),
                onReset = dispatcher.relay(HomeAction.Reset),
                toggleAboutDialog = { aboutDialogVisible = true },
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
            if (exportSupported) {
                Crossfade(windowSizeClass.widthIsExpanded()) { isExpanded ->
                    if (isExpanded) {
                        ExtendedFloatingActionButton(
                            onClick = dispatcher.rememberRelay(Export),
                            icon = { Icon(Icons.Default.Download, contentDescription = "Export") },
                            text = {
                                Text(text = "Export")
                            },
                        )
                    } else {
                        FloatingActionButton(
                            onClick = dispatcher.rememberRelay(Share(selectedSection)),
                            content = { Icon(Icons.Default.Share, contentDescription = "Share") },
                        )
                    }
                }
            } else {
                FloatingActionButton(
                    onClick = dispatcher.rememberRelay(Share(selectedSection)),
                    content = { Icon(Icons.Default.Share, contentDescription = "Share") },
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Expanded -> {
                    ExpandedContent(
                        settings = settings,
                        processingImage = processingImage,
                        dispatcher = dispatcher,
                        windowSizeClass = windowSizeClass,
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
                            processingImage = processingImage,
                            dispatcher = dispatcher,
                            windowSizeClass = windowSizeClass,
                        )
                    }
                }
                WindowWidthSizeClass.Compact -> {
                    CompactContent(
                        settings = settings,
                        selectedSection = selectedSection,
                        processingImage = processingImage,
                        dispatcher = dispatcher,
                        windowSizeClass = windowSizeClass,
                    )
                }
            }
        }

        AboutInfo(
            visible = aboutDialogVisible,
            onDismiss = { aboutDialogVisible = false },
            windowSizeClass = windowSizeClass,
        )

        ColorPickerDialog(
            state = colorPickerState,
            onDismiss = dispatcher.rememberRelay(HomeAction.CloseColorPicker),
            onColorChanged = dispatcher.rememberRelayOf(::UpdateColor),
            toggleMode = dispatcher.rememberRelay(HomeAction.TogglePickerMode),
            selectImage = dispatcher.rememberRelay(HomeAction.PickImageForColor),
        )
    }
}
