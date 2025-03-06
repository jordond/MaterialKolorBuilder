package com.materialkolor.builder.core

import com.mohamedrejeb.calf.core.PlatformContext
import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.events.Event

private const val EVENT_NAME = "popstate"

actual val baseUrl: String
    get() = window.location.origin

actual val isMobile: Boolean
    get() = isMobileBrowser(window.navigator.userAgent, window.navigator.vendor)

/**
 * Only support exporting on desktops
 */
actual val exportSupported: Boolean
    get() = !isMobile

actual val platformContext: PlatformContext
    get() = PlatformContext.INSTANCE

actual fun updatePlatformQueryParams(queryParams: String) {
    // TODO: Add setting to enable/disable undo
    window.history.pushState(null, "", queryParams)
}

actual fun readPlatformQueryParams(): String? {
    return window.location.search.takeIf { it.isNotBlank() }
}

actual fun observePlatformQueryParams(): Flow<String> = callbackFlow {
    val callback: (Event) -> Unit = {
        val params = readPlatformQueryParams()
        if (params != null) {
            trySend(params)
        }
    }

    window.addEventListener(EVENT_NAME, callback)
    awaitClose {
        window.removeEventListener(EVENT_NAME, callback)
    }
}
