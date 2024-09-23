package com.materialkolor.builder.core

import com.mohamedrejeb.calf.core.PlatformContext

/**
 * Whether the platform supports exporting the current theme to code.
 */
actual val exportSupported: Boolean = false

actual val platformContext: PlatformContext
    get() = PlatformContext.INSTANCE