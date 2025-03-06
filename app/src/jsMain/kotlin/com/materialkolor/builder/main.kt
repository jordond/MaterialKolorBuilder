package com.materialkolor.builder

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.materialkolor.builder.core.readPlatformQueryParams
import com.materialkolor.builder.settings.DESTINATION_QUERY_PARAM
import com.materialkolor.builder.settings.store.entity.splitQueryParams
import com.materialkolor.builder.ui.App
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        document.getElementById("warning")?.remove()
        ComposeViewport(document.body!!) {
            LaunchedEffect(Unit) {
                initWasm()
            }

            val initialDestination = extractInitialDestination()
            App(initialDestination)
        }
    }
}

private fun initWasm() {
    js("window.kotlinWasmInitialized = true")
}

private fun extractInitialDestination(): String? {
    val query = readPlatformQueryParams() ?: return null
    return query.splitQueryParams()[DESTINATION_QUERY_PARAM]
}
