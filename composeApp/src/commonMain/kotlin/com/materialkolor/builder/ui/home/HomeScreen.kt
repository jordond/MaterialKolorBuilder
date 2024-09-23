package com.materialkolor.builder.ui.home

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.builder.core.rememberDebounceDispatcher
import com.materialkolor.builder.ui.home.HomeAction.CopyColor
import com.materialkolor.builder.ui.home.HomeAction.Export
import com.materialkolor.builder.ui.home.HomeAction.OpenColorPicker
import com.materialkolor.builder.ui.home.HomeAction.RandomColor
import com.materialkolor.builder.ui.home.HomeAction.Reset
import com.materialkolor.builder.ui.home.HomeAction.SelectImage
import com.materialkolor.builder.ui.home.HomeAction.ToggleDarkMode
import com.materialkolor.builder.ui.home.HomeAction.UpdateContrast
import com.materialkolor.builder.ui.home.HomeAction.UpdatePaletteStyle
import com.materialkolor.builder.ui.home.page.preview.gallery.NavigationDrawerContent
import com.materialkolor.builder.ui.ktx.HandleEvents
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("SnackbarHostState is not found") }

val LocalDrawerState =
    compositionLocalOf<DrawerState> { error("DrawerState is not found") }

val LocalBottomSheetScaffoldState =
    compositionLocalOf<BottomSheetScaffoldState> { error("BottomSheetScaffoldState is not found") }


@Composable
fun HomeScreen() {
    val model = viewModel { HomeModel() }
    val state by model.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    val scaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(SheetValue.Hidden, skipHiddenState = false)
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files -> model.handleImage(files.firstOrNull()) }
    )

    HandleEvents(model) { event ->
        when (event) {
            is HomeModel.Event.ShowSnackbar -> {
                scope.launch {
                    snackbar.showSnackbar(event.message)
                }
            }
        }
    }


    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbar,
        LocalDrawerState provides drawerState,
        LocalBottomSheetScaffoldState provides scaffoldState,
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerContent = {
                ModalDrawerSheet { NavigationDrawerContent() }
            },
        ) {
            HomeScreenScaffold(
                settings = state.settings,
                snackbarState = snackbar,
                processingImage = state.processingImage,
                dispatcher = rememberDebounceDispatcher { action ->
                    when (action) {
                        is UpdateContrast -> model.updateContrast(action.contrast)
                        is UpdatePaletteStyle -> model.updatePaletteStyle(action.style)
                        is SelectImage -> {
                            if (action.image == null) pickerLauncher.launch()
                            else model.selectImagePreset(action.image)
                        }
                        is ToggleDarkMode -> model.toggleDarkMode()
                        is Export -> {} // TODO: Implement export
                        is OpenColorPicker -> {} // TODO: Implement color picker
                        is RandomColor -> model.randomColor()
                        is Reset -> model.reset()
                        is CopyColor -> model.copyColorToClipboard(action.name, action.color)
                    }
                }
            )
        }
    }
}