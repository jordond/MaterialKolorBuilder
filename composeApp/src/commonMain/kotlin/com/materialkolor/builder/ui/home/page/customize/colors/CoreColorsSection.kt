package com.materialkolor.builder.ui.home.page.customize.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.ColorType
import com.materialkolor.builder.ui.home.components.ColorCard
import kotlinx.collections.immutable.PersistentList

@Composable
fun CoreColorsSection(
    onClickColor: (ColorType) -> Unit,
    modifier: Modifier = Modifier,
    coreColors: PersistentList<CoreColor> = CoreColors,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = "Core colors",
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = "Override or set key colors that will be used to generate tonal palettes and schemes.",
            style = MaterialTheme.typography.labelLarge,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            coreColors.forEach { coreColor ->
                ColorCard(
                    color = coreColor.color(),
                    title = coreColor.title,
                    subtitle = coreColor.subtitle,
                    onClick = { onClickColor(coreColor.type) },
                )
            }
        }
    }
}