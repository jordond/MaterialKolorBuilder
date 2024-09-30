package com.materialkolor.builder.ui.components.code

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight

/**
 * A composable that displays code with highlights.
 *
 * Copied from https://raw.githubusercontent.com/SnipMeDev/KodeView/refs/heads/main/kodeview/src/commonMain/kotlin/dev/snipme/kodeview/view/CodeTextView.kt
 */
@Composable
fun CodeTextView(
    highlights: Highlights,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.background(Color.Transparent),
        text = buildAnnotatedString {
            append(highlights.getCode())

            highlights.getHighlights()
                .filterIsInstance<ColorHighlight>()
                .forEach { highlight ->
                    addStyle(
                        style = SpanStyle(color = Color(highlight.rgb).copy(alpha = 1f)),
                        start = highlight.location.start,
                        end = highlight.location.end,
                    )
                }

            highlights.getHighlights()
                .filterIsInstance<BoldHighlight>()
                .forEach { highlight ->
                    addStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold),
                        start = highlight.location.start,
                        end = highlight.location.end,
                    )
                }
        },
    )
}
