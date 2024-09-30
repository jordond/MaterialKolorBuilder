package com.materialkolor.builder.export

import androidx.compose.runtime.Stable

@Stable
data class ExportFile(
    val name: String,
    val content: String,
)
