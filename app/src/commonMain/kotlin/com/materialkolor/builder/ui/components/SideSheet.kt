package com.materialkolor.builder.ui.components

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material.icons.Icons.Default
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.materialkolor.builder.ui.ktx.clickableWithoutRipple
import com.materialkolor.builder.ui.ktx.conditional
import com.materialkolor.builder.ui.ktx.whenNotNull

enum class SideSheetPosition {
    Start,
    End,
}

// TODO: Replace when Material3 includes the SideSheet component
@Composable
fun SideSheet(
    modifier: Modifier = Modifier,
    position: SideSheetPosition = SideSheetPosition.Start,
    initialExpanded: Boolean = false,
    maxWidthFraction: Float = 1f / 2.5f,
    minWidth: Dp = 200.dp,
    visibleWidth: Dp = 60.dp,
    isFloating: Boolean = false,
    displayOverContent: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
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

        val currentSheetWidth = (visibleWidth + (sheetWidth - visibleWidth) * animatedOffsetX)

        val contentWidth =
            if (displayOverContent) null
            else animateDpAsState(targetValue = maxWidth - currentSheetWidth)

        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.whenNotNull(contentWidth) { Modifier.width(it.value) },
            ) {
                Surface(color = contentContainerColor) {
                    content()
                }
            }

            val shape = when (position) {
                SideSheetPosition.Start -> RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                SideSheetPosition.End -> RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
            }

            Box(
                modifier = Modifier
                    .align(
                        if (position == SideSheetPosition.Start) Alignment.CenterStart
                        else Alignment.CenterEnd,
                    )
                    .conditional(isFloating) {
                        Modifier
                            .background(color = contentContainerColor)
                            .padding(vertical = 32.dp)
                    },
            ) {
                Surface(
                    color = containerColor,
                    shape = shape,
                    modifier = Modifier
                        .width(sheetWidth)
                        .clipToBounds()
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                val offset =
                                    ((1 - animatedOffsetX) * (sheetWidth - visibleWidth).toPx()).toInt()
                                val xOffset = when (position) {
                                    SideSheetPosition.Start -> -offset
                                    SideSheetPosition.End -> offset
                                }
                                placeable.place(xOffset, 0)
                            }
                        },
                ) {
                    val panel: @Composable () -> Unit = remember(isExpanded) {
                        {
                            ExpandCollapsePanel(
                                isExpanded = isExpanded,
                                onClick = { isExpanded = !isExpanded },
                                sheetPosition = position,
                                visibleWidth = visibleWidth,
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        if (position == SideSheetPosition.End) {
                            panel()
                        }

                        Box(
                            modifier = Modifier.weight(1f),
                        ) {
                            sheetContent()
                        }

                        if (position == SideSheetPosition.Start) {
                            panel()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandCollapsePanel(
    isExpanded: Boolean,
    onClick: () -> Unit,
    visibleWidth: Dp,
    sheetPosition: SideSheetPosition,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(visibleWidth)
            .clickableWithoutRipple(onClick)
            .fillMaxHeight(),
    ) {
        IconButton(
            onClick = onClick,
        ) {
            val icon = when (sheetPosition) {
                SideSheetPosition.Start -> if (isExpanded) Default.ChevronLeft else Default.ChevronRight
                SideSheetPosition.End -> if (isExpanded) Default.ChevronRight else Default.ChevronLeft
            }

            Icon(
                imageVector = icon,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}
