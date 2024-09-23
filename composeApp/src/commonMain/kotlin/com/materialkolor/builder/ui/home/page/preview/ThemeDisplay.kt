package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(SectionDivider),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(InnerDivider),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        MainColors.forEach { group ->
                            ColorGroupContainer(group, modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.width(SectionDivider - InnerDivider))

                        ColorGroupContainer(Theme.Groups.Error, modifier = Modifier.weight(1f))
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SectionDivider),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                            modifier = Modifier.weight(1f),
                        ) {
                            Row {
                                Surface.forEach { themeColor ->
                                    ColorBox(themeColor, lines = 4, modifier = Modifier.weight(1f))
                                }
                            }

                            Row {
                                SurfaceContainer.forEach { themeColor ->
                                    ColorBox(themeColor, lines = 4, modifier = Modifier.weight(1f))
                                }
                            }

                            Row {
                                MiscColors.forEach { themeColor ->
                                    SingleLineColorBox(themeColor, modifier = Modifier.weight(1f))
                                }
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                            modifier = Modifier.width(IntrinsicSize.Min),
                        ) {
                            ColorPairContainer(pair = InverseSurfacePair)
                            SingleLineColorBox(
                                themeColor = Theme.Colors.InversePrimary,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(SectionDivider),
                            ) {
                                SingleLineColorBox(Theme.Colors.Scrim)
                                SingleLineColorBox(Theme.Colors.Shadow)
                            }
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
) {
    Column(
        modifier = modifier,
    ) {
        CompositionLocalProvider(LocalContentColor provides pair.onColor.color()) {
            ColorBox(pair.color, modifier = Modifier.fillMaxWidth())
        }

        CompositionLocalProvider(LocalContentColor provides pair.color.color()) {
            SingleLineColorBox(pair.onColor, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ColorBox(themeColor: ThemeColor, lines: Int = 3, modifier: Modifier = Modifier) {
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

@Composable
fun SingleLineColorBox(
    themeColor: ThemeColor,
    modifier: Modifier = Modifier,
) {
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