package com.materialkolor.builder.ui.ktx

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun Color.toHex(includePrefix: Boolean = true): String {
    val alpha = (alpha * 255).roundToInt()
    val red = (red * 255).roundToInt()
    val green = (green * 255).roundToInt()
    val blue = (blue * 255).roundToInt()

    return buildString {
        if (includePrefix) {
            append('#')
        }
        if (alpha < 255) {
            append(alpha.toString(16).padStart(2, '0'))
        }
        append(red.toString(16).padStart(2, '0'))
        append(green.toString(16).padStart(2, '0'))
        append(blue.toString(16).padStart(2, '0'))
    }.uppercase()
}