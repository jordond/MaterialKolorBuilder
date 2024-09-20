package com.materialkolor.builder.settings.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import materialkolorbuilder.composeapp.generated.resources.Res
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_1
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_2
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_3
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_4
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_5
import materialkolorbuilder.composeapp.generated.resources.content_based_color_scheme_6
import org.jetbrains.compose.resources.DrawableResource

@Immutable
sealed class Image {
    abstract val id: String

    @Immutable
    data class Resource(override val id: String, val drawableResource: DrawableResource) : Image() {
        constructor(
            id: Int,
            drawableResource: DrawableResource,
        ) : this("res-$id", drawableResource)
    }

    @Immutable
    data class Custom(val data: ImageBitmap) : Image() {
        override val id: String = "custom"
    }
}

object ImagePresets {
    val one = 1 to Res.drawable.content_based_color_scheme_1
    val two = 2 to Res.drawable.content_based_color_scheme_2
    val three = 3 to Res.drawable.content_based_color_scheme_3
    val four = 4 to Res.drawable.content_based_color_scheme_4
    val five = 5 to Res.drawable.content_based_color_scheme_5
    val six = 6 to Res.drawable.content_based_color_scheme_6

    val all = listOf(one, two, three, four, five, six).map { (index, res) ->
        Image.Resource(index, res)
    }
}