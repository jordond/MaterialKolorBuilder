package com.materialkolor.builder.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.ImageColorPicker
import com.github.skydoves.colorpicker.compose.PaletteContentScale
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.materialkolor.builder.settings.model.KeyColor
import com.materialkolor.builder.ui.home.LocalSnackbarHostState
import com.materialkolor.builder.ui.ktx.launch
import com.materialkolor.builder.ui.ktx.toHex

@Composable
fun ColorPickerDialog(
    state: ColorPickerState?,
    onColorChanged: (Color) -> Unit,
    onDismiss: () -> Unit,
    toggleMode: () -> Unit,
    selectImage: () -> Unit,
    modifier: Modifier = Modifier,
    controller: ColorPickerController = rememberColorPickerController(),
) {
    if (state != null) {
        ColorPickerDialog(
            state = state,
            onColorChanged = onColorChanged,
            onConfirm = onDismiss,
            onDismiss = {
                onColorChanged(state.initial)
                onDismiss()
            },
            toggleMode = toggleMode,
            selectImage = selectImage,
            modifier = modifier,
            controller = controller,
        )
    }
}

@Composable
fun ColorPickerDialog(
    state: ColorPickerState,
    onColorChanged: (Color) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    toggleMode: () -> Unit,
    selectImage: () -> Unit,
    modifier: Modifier = Modifier,
    controller: ColorPickerController = rememberColorPickerController(),
) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboardManager.current
    val snackbar = LocalSnackbarHostState.current

    var selectedColor by remember { mutableStateOf(state.initial) }
    var selectedHex by remember { mutableStateOf(state.initial.toHex(includePrefix = false)) }

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Card(
                modifier = Modifier
                    .requiredWidthIn(min = 300.dp, max = 400.dp)
                    .requiredHeightIn(min = 450.dp, max = 650.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 32.dp, bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Palette,
                        contentDescription = "Hero icon",
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    Text(
                        text = "Color Picker",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Normal,
                        ),
                    )

                    Text(
                        text = "Select a color to use as the ${state.keyColor.name} color",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                        ),
                    )

                    if (state.keyColor.name == KeyColor.Primary.name) {
                        Text(
                            text = "The primary color will be used as the new seed color.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                            ),
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Hex Color",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                            ),
                            modifier = Modifier.weight(1f),
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            OutlinedTextField(
                                value = selectedHex,
                                maxLines = 1,
                                onValueChange = {}, // TODO :Implement user input
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                ),
                                prefix = {
                                    Text(
                                        text = "#",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Normal,
                                        ),
                                    )
                                },
                                modifier = Modifier.width(125.dp),
                            )

                            FilledTonalIconButton(
                                enabled = clipboard.hasText(),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    val color = clipboard.getText()?.toString()?.parse()
                                    if (color != null) {
                                        controller.selectByColor(color, fromUser = true)
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ContentPaste,
                                    contentDescription = "Paste Color",
                                )
                            }

                            FilledIconButton(
                                enabled = true, // TODO: Ensure typed in hex is valid
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    val value = selectedColor.toHex()
                                    clipboard.setText(AnnotatedString(value))
                                    snackbar.launch(scope, "Copied $value to clipboard")
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ContentCopy,
                                    contentDescription = "Copy Color",
                                )
                            }
                        }
                    }

                    ColorPicker(
                        state = state,
                        controller = controller,
                        onSelectImage = selectImage,
                        onChange = { envelope ->
                            selectedColor = envelope.color
                            selectedHex = envelope.color.toHex(includePrefix = false)

                            if (envelope.fromUser) {
                                onColorChanged(envelope.color)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .weight(1f),
                    )

                    FilledTonalButton(
                        onClick = toggleMode,
                    ) {
                        val icon =
                            if (state.mode == ColorPickerMode.HSV) Icons.Outlined.Image
                            else Icons.Outlined.Palette

                        val text = if (state.mode == ColorPickerMode.HSV) "Image" else "Color"
                        Icon(imageVector = icon, contentDescription = "Toggle Mode")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "From $text")
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.End),
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }

                        TextButton(onClick = onConfirm) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ColorPicker(
    state: ColorPickerState,
    onChange: (ColorEnvelope) -> Unit,
    modifier: Modifier = Modifier,
    onSelectImage: () -> Unit = {},
    controller: ColorPickerController = rememberColorPickerController(),
) {
    Crossfade(
        targetState = state,
        modifier = modifier,
    ) { currentState ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentState.mode) {
                ColorPickerMode.HSV -> {
                    HsvColorPicker(
                        modifier = Modifier,
                        initialColor = currentState.initial,
                        controller = controller,
                        drawOnPosSelected = {
                            drawColorIndicator(
                                controller.selectedPoint.value,
                                controller.selectedColor.value,
                            )
                        },
                        onColorChanged = onChange,
                    )
                }
                ColorPickerMode.Image -> {
                    if (currentState.image == null) {
                        OutlinedButton(
                            onClick = onSelectImage,
                            modifier = Modifier.align(Alignment.Center),
                        ) {
                            if (currentState.loading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Text("Select Image")
                            }
                        }
                    } else {
                        ImageColorPicker(
                            modifier = Modifier.fillMaxSize(),
                            paletteImageBitmap = currentState.image,
                            controller = controller,
                            drawOnPosSelected = {
                                drawColorIndicator(
                                    controller.selectedPoint.value,
                                    controller.selectedColor.value,
                                )
                            },
                            onColorChanged = onChange,
                            paletteContentScale = PaletteContentScale.FIT,
                        )
                    }
                }
            }
        }
    }
}

private fun String.parse(): Color? = try {
    val cleanColorString = removePrefix("#")
    when (cleanColorString.length) {
        6 -> Color(cleanColorString.toLong(16) or 0xFF000000) // RGB
        8 -> Color(cleanColorString.toLong(16)) // ARGB
        else -> null // Invalid format
    }
} catch (exception: NumberFormatException) {
    // Invalid hex value
    null
}