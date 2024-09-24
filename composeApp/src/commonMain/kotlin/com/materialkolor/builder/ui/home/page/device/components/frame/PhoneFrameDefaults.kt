package com.materialkolor.builder.ui.home.page.device.components.frame

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PhoneFrameSize(
    val width: Dp,
    val height: Dp,
)

data class PhoneFramePadding(
    val outer: Dp,
    val inner: Dp,
    val thickness: Dp,
)

object PhoneFrameDefaults {

    val width = 400.dp
    val height = 800.dp

    private val androidOuterPadding = 20.dp
    private val androidInnerPadding = 10.dp
    private val androidThickness = 10.dp
    private val iosOuterPadding = 40.dp
    private val iosInnerPadding = 30.dp
    private val iosThickness = 10.dp

    fun size(width: Dp = this.width, height: Dp = this.height) = PhoneFrameSize(width, height)

    fun framePaddingIos(
        outer: Dp = iosOuterPadding,
        inner: Dp = iosInnerPadding,
        thickness: Dp = iosThickness,
    ) = PhoneFramePadding(outer, inner, thickness)

    fun androidFramePadding(
        outer: Dp = androidOuterPadding,
        inner: Dp = androidInnerPadding,
        thickness: Dp = androidThickness,
    ) = PhoneFramePadding(outer, inner, thickness)
}