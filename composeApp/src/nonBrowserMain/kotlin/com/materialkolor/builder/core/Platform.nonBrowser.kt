package com.materialkolor.builder.core

private var cache: String? = null

actual fun updatePlatformQueryParams(queryParams: String) {
    cache = queryParams
}

actual fun readPlatformQueryParams(): String? {
    return cache?.takeIf { it.isNotBlank() }
}

actual val baseUrl: String = "https://materialkolor.com"
