package com.materialkolor.builder.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import co.touchlab.kermit.Logger
import com.materialkolor.builder.ui.ktx.conditional
import com.materialkolor.builder.ui.ktx.debugBorder
import com.materialkolor.builder.ui.ktx.whenNotNull
import kotlin.math.roundToInt

enum class SideSheetPosition {
    Start,
    End,
}

// TODO: Replace when Material3 includes the SideSheet component
@OptIn(ExperimentalFoundationApi::class)
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

        val currentSheetWidth = if (displayOverContent) {
            (visibleWidth + (sheetWidth - visibleWidth) * animatedOffsetX)
        } else {
            if (isExpanded) sheetWidth else visibleWidth
        }

        val contentWidth = if (displayOverContent) {
            null
        } else {
            animateDpAsState(targetValue = maxWidth - currentSheetWidth)
        }

        val density = LocalDensity.current
        val anchors = remember(sheetWidth, visibleWidth, position) {
            DraggableAnchors {
                when (position) {
                    SideSheetPosition.Start -> {
                        DragValue.Collapsed at with(density) { -sheetWidth.toPx() + visibleWidth.toPx() }
                        DragValue.Expanded at 0f
                    }
                    SideSheetPosition.End -> {
                        DragValue.Expanded at with(density) { -sheetWidth.toPx() + visibleWidth.toPx() }
                        DragValue.Collapsed at 0f
                    }
                }
            }
        }

        val velocityThreshold = AnchoredDraggableDefaults.VelocityThreshold()
        val draggableState = remember(position) {
            AnchoredDraggableState(
                initialValue = if (isExpanded) DragValue.Expanded else DragValue.Collapsed,
                anchors = anchors,
                positionalThreshold = AnchoredDraggableDefaults.PositionalThreshold,
                velocityThreshold = velocityThreshold,
                snapAnimationSpec = AnchoredDraggableDefaults.SnapAnimationSpec,
                decayAnimationSpec = AnchoredDraggableDefaults.DecayAnimationSpec,
            )
        }

        LaunchedEffect(draggableState.currentValue) {
            isExpanded = draggableState.currentValue == DragValue.Expanded
        }

        Box(modifier = modifier.fillMaxSize()) {
            val contentAlignment = when (position) {
                SideSheetPosition.Start -> Alignment.CenterEnd
                SideSheetPosition.End -> Alignment.CenterStart
            }

            Box(
                contentAlignment = contentAlignment,
                modifier = Modifier.fillMaxSize(),
            ) {
                Surface(
                    color = contentContainerColor,
                    modifier = Modifier
                        .debugBorder(Color.Blue)
                        .whenNotNull(contentWidth) { Modifier.width(it.value) },
                ) {
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
                            .background(color = Color.Yellow)
                            .padding(vertical = 32.dp)
                    }
                    .anchoredDraggable(
                        state = draggableState,
                        orientation = Orientation.Horizontal,
                        reverseDirection = position == SideSheetPosition.End,
                        enabled = true,
                    ),
            ) {
                Surface(
                    color = containerColor,
                    shape = shape,
                    modifier = Modifier
                        .width(sheetWidth)
                        .clipToBounds()
                        .offset {
                            IntOffset(
                                x = when (position) {
                                    SideSheetPosition.Start -> draggableState.offset.roundToInt()
                                    SideSheetPosition.End -> -draggableState.offset.roundToInt()
                                },
                                y = 0,
                            )
                        },
                ) {
                    val panel: @Composable () -> Unit = remember(isExpanded) {
                        {
                            ExpandCollapsePanel(
                                isExpanded = isExpanded,
                                visibleWidth = visibleWidth,
                                sheetPosition = position,
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

private enum class DragValue {
    Expanded,
    Collapsed
}

// Update ExpandCollapsePanel to remove the click handler
@Composable
private fun ExpandCollapsePanel(
    isExpanded: Boolean,
    visibleWidth: Dp,
    sheetPosition: SideSheetPosition,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(visibleWidth)
            .fillMaxHeight(),
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

object AnchoredDraggableDefaults {

    /** The default spec for snapping, a tween spec */
    val SnapAnimationSpec: AnimationSpec<Float> = tween()

    /** The default positional threshold, 50% of the distance */
    val PositionalThreshold: (Float) -> Float = { distance -> distance * 0.5f }

    /** The default velocity threshold, 125 dp per second */
    @Composable
    fun VelocityThreshold(): () -> Float {
        val density = LocalDensity.current
        return { with(density) { 125.dp.toPx() } }
    }

    /** The default spec for decaying, an exponential decay */
    val DecayAnimationSpec: DecayAnimationSpec<Float> = exponentialDecay()
}

