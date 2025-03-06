package com.materialkolor.builder.core

actual val shareToClipboard: Boolean = true

actual fun shareUrl(url: String) {
    copyTextToClipboard(url)
}
