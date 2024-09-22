package com.materialkolor.builder.ui.home.page.customize

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import com.materialkolor.builder.settings.model.SeedImage
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImagePresetRow(
    imagePresets: PersistentList<SeedImage.Resource>,
    selectedImage: SeedImage?,
    onPresetSelected: (SeedImage.Resource) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        imagePresets.forEachIndexed { index, image ->
            val isSelected = image.id == selectedImage?.id
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1f else if (isHovered) 0.9f else 0.8f
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .hoverable(interactionSource)
                    .weight(1f)
                    .aspectRatio(1f)
                    .scale(scale)
                    .clip(
                        if (isSelected) MaterialTheme.shapes.small
                        else MaterialTheme.shapes.medium,
                    )
                    .clickable { onPresetSelected(image) }
            ) {
                Image(
                    painter = painterResource(image.drawableResource),
                    contentDescription = "Preset image #${index + 1}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                if (isSelected) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize(0.3f)
                            .aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .fillMaxSize(0.6f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}
