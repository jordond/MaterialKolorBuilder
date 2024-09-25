package com.materialkolor.builder.core

import com.mohamedrejeb.calf.core.PlatformContext
import kotlinx.browser.window

/**
 * Whether the platform supports exporting the current theme to code.
 */
actual val exportSupported: Boolean = true

actual val platformContext: PlatformContext
    get() = PlatformContext.INSTANCE

actual fun updatePlatformQueryParams(queryParams: String) {
    window.history.replaceState(null, "", queryParams)
}

actual fun readPlatformQueryParams(): String? {
    return window.location.search.takeIf { it.isNotBlank() }
}