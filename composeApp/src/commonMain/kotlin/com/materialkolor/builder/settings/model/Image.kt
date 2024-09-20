package com.materialkolor.builder.settings.model

import androidx.compose.ui.graphics.ImageBitmap
import materialkolorbuilder.composeapp.generated.resources.Res
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_1
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_2
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_3
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_4
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_5
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_6
import org.jetbrains.compose.resources.DrawableResource

sealed class Image {
    abstract val id: String

    data class Resource(override val id: String, val drawableResource: DrawableResource) : Image() {
        constructor(
            id: Int,
            drawableResource: DrawableResource,
        ) : this("res-$id", drawableResource)
    }

    data class Custom(val data: ImageBitmap) : Image() {
        override val id: String = "custom"
    }
}

object ImagePresets {
    val one = Res.drawable.content_based_color_scheme_1
    val two = Res.drawable.content_based_color_scheme_2
    val three = Res.drawable.content_based_color_scheme_3
    val four = Res.drawable.content_based_color_scheme_4
    val five = Res.drawable.content_based_color_scheme_5
    val six = Res.drawable.content_based_color_scheme_6

    val all = listOf(one, two, three, four, five, six).mapIndexed { index, res ->
        Image.Resource(index, res)
    }
}