package com.materialkolor.builder.ui.home.page.preview.gallery.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.materialkolor.builder.ui.home.page.preview.gallery.GalleryChildSection
import com.materialkolor.builder.ui.home.page.preview.gallery.GallerySection
import com.materialkolor.builder.ui.home.page.preview.gallery.GallerySectionDefaults
import com.materialkolor.builder.ui.home.page.preview.gallery.itemPadding

@Composable
fun ActionGallerySection(
    expanded: Boolean,
    toggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    minWidth: Dp = GallerySectionDefaults.MinWidth,
    width: Dp = GallerySectionDefaults.Width,
    itemPadding: Dp = GallerySectionDefaults.ItemPadding,
) {
    GallerySection(
        title = "Actions",
        expanded = expanded,
        toggle = toggle,
        modifier = modifier,
    ) {
        GalleryChildSection(
            title = "Common buttons",
            infoUrl = "https://developer.android.com/jetpack/compose/components/button"
        ) {
            CommonButtons(minWidth, width, itemPadding)
        }

        GalleryChildSection(
            title = "Floating action buttons",
            infoUrl = "https://developer.android.com/jetpack/compose/components/fab"
        ) {
            FloatingActionButtons(minWidth, width, itemPadding)
        }

        GalleryChildSection(
            title = "Icon buttons",
            infoUrl = "https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#IconButton(kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.material3.IconButtonColors,androidx.compose.foundation.interaction.MutableInteractionSource,kotlin.Function0)"
        ) {
            IconButtons(minWidth, width, itemPadding)
        }
    }
}

@Composable
private fun CommonButtons(
    minWidth: Dp,
    width: Dp,
    itemPadding: Dp,
) {
    OutlinedCard {
        Column(
            modifier = Modifier
                .requiredWidthIn(minWidth)
                .widthIn(minWidth, width)
                .padding(itemPadding)
        ) {
            Row {
                ElevatedButton(
                    onClick = {},
                    content = { Text("Elevated") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                ElevatedButton(
                    onClick = {},
                    content = {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Icon")
                    },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f)
                )
                ElevatedButton(
                    enabled = false,
                    onClick = {},
                    content = { Text("Elevated") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
            }
            Row {
                Button(
                    onClick = {},
                    content = { Text("Filled") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                Button(
                    onClick = {},
                    content = {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Icon")
                    },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                Button(
                    enabled = false,
                    onClick = {},
                    content = { Text("Filled") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
            }
            Row {
                FilledTonalButton(
                    onClick = {},
                    content = { Text("Tonal") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                FilledTonalButton(
                    onClick = {},
                    content = {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Icon")
                    },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                FilledTonalButton(
                    enabled = false,
                    onClick = {},
                    content = { Text("Tonal") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
            }
            Row {
                OutlinedButton(
                    onClick = {},
                    content = { Text("Outlined") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                OutlinedButton(
                    onClick = {},
                    content = {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Icon")
                    },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                OutlinedButton(
                    enabled = false,
                    onClick = {},
                    content = { Text("Outlined") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
            }
            Row {
                TextButton(
                    onClick = {},
                    content = { Text("Text") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                TextButton(
                    onClick = {},
                    content = {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Icon")
                    },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
                TextButton(
                    enabled = false,
                    onClick = {},
                    content = { Text("Text") },
                    modifier = Modifier
                        .itemPadding()
                        .weight(1f),
                )
            }
        }
    }
}

@Composable
private fun FloatingActionButtons(
    minWidth: Dp,
    width: Dp,
    itemPadding: Dp,
) {
    OutlinedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .requiredWidthIn(minWidth)
                .width(width)
                .padding(itemPadding),
        ) {
            SmallFloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(itemPadding),
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
            ExtendedFloatingActionButton(
                onClick = {},
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Create") },
                modifier = Modifier.padding(itemPadding),
            )
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(itemPadding),
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
            LargeFloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(itemPadding),
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
private fun IconButtons(
    minWidth: Dp,
    width: Dp,
    itemPadding: Dp,
) {
    OutlinedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .requiredWidthIn(minWidth)
                .width(width)
                .padding(itemPadding),
        ) {
            IconButton(
                onClick = {},
                enabled = true,
                content = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )
            IconButton(
                onClick = {},
                enabled = false,
                content = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )
        }
    }
}