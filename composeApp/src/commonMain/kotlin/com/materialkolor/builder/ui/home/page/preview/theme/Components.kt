package com.materialkolor.builder.ui.home.page.preview.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.builder.ui.home.page.preview.model.ThemeColor
import com.materialkolor.builder.ui.home.page.preview.model.ThemeGroup
import com.materialkolor.builder.ui.home.page.preview.model.ThemePair
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.BoxPadding
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.InnerDivider

@Composable
fun ColorGroupContainer(
    group: ThemeGroup,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(InnerDivider),
        modifier = modifier,
    ) {
        ColorPairContainer(group.main)
        ColorPairContainer(group.container)
    }
}

@Composable
fun ColorPairContainer(
    pair: ThemePair,
    modifier: Modifier = Modifier,
    lines: Int = 3,
) {
    Column(
        modifier = modifier,
    ) {
        CompositionLocalProvider(LocalContentColor provides pair.onColor.color()) {
            ColorBox(pair.color, lines = lines, modifier = Modifier.fillMaxSize())
        }

        CompositionLocalProvider(LocalContentColor provides pair.color.color()) {
            SingleLineColorBox(pair.onColor, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ColorBox(
    themeColor: ThemeColor,
    lines: Int = 3,
    modifier: Modifier = Modifier,
    textColor: Color? = null,
) {
    ContentColorWrapper(themeColor, textColor) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .background(themeColor.color())
                .padding(BoxPadding)
        ) {
            Text(text = themeColor.title, minLines = lines)
            Text(text = themeColor.swatchNumber, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun SingleLineColorBox(
    themeColor: ThemeColor,
    modifier: Modifier = Modifier,
    textColor: Color? = null,
) {
    ContentColorWrapper(themeColor, textColor) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(themeColor.color())
                .padding(BoxPadding)
        ) {
            Text(text = themeColor.title, modifier = Modifier.weight(1f))
            Text(text = themeColor.swatchNumber)
        }
    }
}

@Composable
private fun ContentColorWrapper(
    themeColor: ThemeColor,
    textColor: Color? = null,
    content: @Composable () -> Unit,
) {
    val contentColor by animateColorAsState(
        textColor ?: contentColorFor(themeColor.color())
    )

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        content()
    }
}