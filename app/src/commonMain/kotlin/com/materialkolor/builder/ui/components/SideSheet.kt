package com.materialkolor.builder.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min

// TODO: Replace when Material3 includes the SideSheet component
@Composable
fun SideSheet(
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    maxWidthFraction: Float = 1f / 2.5f,
    minWidth: Dp = 200.dp,
    visibleWidth: Dp = 60.dp,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints {
        val maxSheetWidth = maxWidth * maxWidthFraction
        val sheetWidth = min(max(minWidth, maxSheetWidth), maxWidth)

        var isExpanded by remember { mutableStateOf(initialExpanded) }

        val animatedOffsetX by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
        )

        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = visibleWidth),
            ) {
                content()
            }

            val surfaceColor = MaterialTheme.colorScheme.surfaceContainer

            Surface(
                color = surfaceColor,
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(sheetWidth)
                    .padding(vertical = 32.dp)
                    .fillMaxHeight()
                    .clipToBounds()
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            val xOffset =
                                -((1 - animatedOffsetX) * (sheetWidth - visibleWidth).toPx()).toInt()
                            placeable.place(xOffset, 0)
                        }
                    },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        sheetContent()
                    }

                    val tabColor by animateColorAsState(
                        if (isExpanded) surfaceColor else MaterialTheme.colorScheme.surfaceContainerHighest,
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(visibleWidth)
                            .fillMaxHeight()
                            .background(tabColor),
                    ) {
                        IconButton(
                            onClick = { isExpanded = !isExpanded },
                        ) {
                            val icon =
                                if (isExpanded) Icons.Default.ChevronLeft
                                else Icons.Default.ChevronRight
                            Icon(
                                imageVector = icon,
                                contentDescription = if (isExpanded) "Collapse" else "Expand",
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
