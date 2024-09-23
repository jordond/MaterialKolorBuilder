package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.page.preview.model.Theme
import com.materialkolor.builder.ui.home.page.preview.model.ThemeColor
import com.materialkolor.builder.ui.home.page.preview.model.ThemeGroup
import com.materialkolor.builder.ui.home.page.preview.model.ThemePair
import com.materialkolor.builder.ui.ktx.debugBorder
import com.materialkolor.builder.ui.theme.AppTypography
import com.materialkolor.builder.ui.theme.createThemeState
import com.materialkolor.ktx.isLight
import kotlinx.collections.immutable.persistentListOf

enum class ThemeMode {
    Light,
    Dark,
}

@Composable
fun LightThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
) {
    ThemeDisplay(ThemeMode.Light, createThemeState(settings, isDark = false), modifier)
}

@Composable
fun DarkThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
) {
    ThemeDisplay(ThemeMode.Dark, createThemeState(settings, isDark = true), modifier)
}

private val MainColors =
    persistentListOf(Theme.Groups.Primary, Theme.Groups.Secondary, Theme.Groups.Tertiary)

private val Surface =
    persistentListOf(Theme.Colors.SurfaceDim, Theme.Colors.Surface, Theme.Colors.SurfaceBright)

private val SurfaceContainer = persistentListOf(
    Theme.Colors.SurfaceContainerLowest,
    Theme.Colors.SurfaceContainerLow,
    Theme.Colors.SurfaceContainer,
    Theme.Colors.SurfaceContainerHigh,
    Theme.Colors.SurfaceContainerHighest,
)

private val MiscColors = persistentListOf(
    Theme.Colors.OnSurface,
    Theme.Colors.OnSurfaceVariant,
    Theme.Colors.Outline,
    Theme.Colors.OutlineVariant,
)

private val InverseSurfacePair = ThemePair(
    Theme.Colors.InverseSurface,
    Theme.Colors.InverseOnSurface,
)

private val SectionDivider = 16.dp
private val InnerDivider = 6.dp
private val BoxPadding = 12.dp
private val TextStyle
    @Composable
    get() = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light)

@Composable
private fun ThemeDisplay(
    theme: ThemeMode,
    state: DynamicMaterialThemeState,
    modifier: Modifier = Modifier,
) {
    DynamicMaterialTheme(
        state = state,
        animate = true,
        typography = AppTypography,
    ) {
        ThemeDisplay(theme, modifier)
    }
}

@Composable
private fun ThemeDisplay(
    theme: ThemeMode,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${theme.name} Theme",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = SectionDivider),
            )

            CompositionLocalProvider(LocalTextStyle provides TextStyle) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SectionDivider),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SectionDivider),
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            MainColors.forEach { group ->
                                ColorGroupContainer(group, modifier = Modifier.weight(1f))
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            Row {
                                Surface.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                SurfaceContainer.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                MiscColors.forEach { themeColor ->
                                    MiscColorBox(
                                        themeColor = themeColor,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }
                        }
                    }

                    // Error and misc column
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SectionDivider),
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .weight(0.3f)
                            .fillMaxHeight()
                    ) {
                        ColorGroupContainer(Theme.Groups.Error)

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            ColorPairContainer(
                                pair = InverseSurfacePair,
                                modifier = Modifier.fillMaxHeight(),
                            )

                            MiscColorBox(
                                themeColor = Theme.Colors.InversePrimary,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            MiscColorBox(Theme.Colors.Scrim, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}

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
fun MiscColorBox(
    themeColor: ThemeColor,
    modifier: Modifier = Modifier,
) {
    val contentColor by animateColorAsState(
        if (themeColor.color().isLight()) MaterialTheme.colorScheme.inverseSurface
        else MaterialTheme.colorScheme.surface
    )

    SingleLineColorBox(themeColor, modifier, contentColor)
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
                .width(IntrinsicSize.Min)
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