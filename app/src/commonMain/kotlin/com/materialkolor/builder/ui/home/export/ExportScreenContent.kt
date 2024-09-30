package com.materialkolor.builder.ui.home.export

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.core.Dispatcher
import com.materialkolor.builder.export.ExportOptions
import com.materialkolor.builder.ui.LocalWindowSizeClass
import com.materialkolor.builder.ui.components.SideSheet
import com.materialkolor.builder.ui.components.SideSheetPosition
import com.materialkolor.builder.ui.home.HomeAction
import com.materialkolor.builder.ui.home.HomeAction.OpenColorPicker
import com.materialkolor.builder.ui.home.HomeAction.RandomColor
import com.materialkolor.builder.ui.home.HomeAction.SelectImage
import com.materialkolor.builder.ui.home.HomeAction.UpdateContrast
import com.materialkolor.builder.ui.home.HomeAction.UpdatePaletteStyle
import com.materialkolor.builder.ui.home.LocalSnackbarHostState
import com.materialkolor.builder.ui.home.preview.customize.CustomizeSection
import com.materialkolor.builder.ui.ktx.launch

@Composable
fun ExportScreenContent(
    options: ExportOptions,
    processingImage: Boolean,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = LocalWindowSizeClass.current,
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            ExportExpandedContent(
                options = options,
                modifier = modifier,
                dispatcher = dispatcher,
                processingImage = processingImage,
                windowSizeClass = windowSizeClass,
            )
        }
        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Not supported", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Please expand the window to view the export settings, or view on a larger screen.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 300.dp),
                )
            }
        }
    }
}

@Composable
fun ExportExpandedContent(
    options: ExportOptions,
    processingImage: Boolean,
    dispatcher: Dispatcher<HomeAction>,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = LocalWindowSizeClass.current,
) {
    SideSheet(
        position = SideSheetPosition.End,
        initialExpanded = true,
        isFloating = true,
        displayOverContent = false,
        sheetContent = {
            CustomizeSection(
                settings = options.settings,
                onSelectImage = dispatcher.rememberRelayOf(::SelectImage),
                onRandomColor = dispatcher.rememberRelay(RandomColor),
                openColorPicker = dispatcher.rememberRelayOf(::OpenColorPicker),
                onUpdatePaletteStyle = dispatcher.rememberRelayOf(::UpdatePaletteStyle),
                onUpdateContrast = dispatcher.rememberRelayOf(::UpdateContrast),
                processingImage = processingImage,
                windowSizeClass = windowSizeClass,
            )
        },
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text("Export Options")

            var selected by remember { mutableStateOf(options.files.first()) }
            val clipboard = LocalClipboardManager.current
            val snackbar = LocalSnackbarHostState.current
            val scope = rememberCoroutineScope()
            FileListContainer(
                selected = selected,
                files = options.files,
                onSelected = { selected = it },
                onClick = {
                    clipboard.setText(AnnotatedString(selected.content))
                    snackbar.launch(scope, "Copied the contents of ${selected.name} to clipboard")
                },
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}
