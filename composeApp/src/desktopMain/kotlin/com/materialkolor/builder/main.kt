package com.materialkolor.builder

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.materialkolor.builder.ui.App
import materialkolorbuilder.composeapp.generated.resources.Res
import materialkolorbuilder.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MaterialKolor Builder",
        icon = painterResource(Res.drawable.icon),
        state = rememberWindowState(placement = WindowPlacement.Maximized),
    ) {
        App()
    }
}