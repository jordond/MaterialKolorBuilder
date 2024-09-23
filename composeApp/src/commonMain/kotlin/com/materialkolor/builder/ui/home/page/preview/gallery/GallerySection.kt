package com.materialkolor.builder.ui.home.page.preview.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.ui.ktx.debugBorder
import com.materialkolor.builder.ui.theme.LocalUrlLauncher

fun Modifier.itemPadding() = padding(horizontal = 8.dp, vertical = 4.dp)

object GallerySectionDefaults {
    val MinWidth = 200.dp
    val Width = 400.dp
    val ItemPadding = 8.dp
    val BoxPadding = 16.dp
}

@Composable
fun GallerySection(
    title: String,
    modifier: Modifier = Modifier,
    expanded: Boolean = true,
    toggle: (Boolean) -> Unit = {},
    boxPadding: Dp = GallerySectionDefaults.BoxPadding,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .animateContentSize()
            .padding(boxPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )

            IconButton(
                onClick = { toggle(!expanded) },
                modifier = Modifier
                    .padding(8.dp)
                    .size(16.dp),
            ) {
                val icon =
                    if (expanded) Icons.Outlined.ArrowUpward
                    else Icons.Outlined.ArrowDownward

                val text = if (expanded) "Collapse" else "Expand"

                Icon(imageVector = icon, contentDescription = text)
            }
        }

        AnimatedVisibility(expanded) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun GalleryChildSection(
    title: String,
    infoUrl: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column {
        val urlLauncher = LocalUrlLauncher.current
        Row(
            modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(title)
            IconButton(
                onClick = { urlLauncher.launch(infoUrl) },
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp),
            ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
            }
        }

        content()
    }
}