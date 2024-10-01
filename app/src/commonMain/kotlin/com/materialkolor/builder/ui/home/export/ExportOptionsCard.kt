package com.materialkolor.builder.ui.home.export

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.builder.export.ExportOptions

private enum class ExportMode(val value: String) {
    MaterialKolor("Material Kolor"),
    Standard("Standard"),
}

@Composable
fun ExportOptionsCard(
    options: ExportOptions,
    toggleMode: () -> Unit,
    updateOptions: (ExportOptions) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedMode = remember(options) {
        when (options) {
            is ExportOptions.MaterialKolor -> ExportMode.MaterialKolor
            is ExportOptions.Standard -> ExportMode.Standard
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = "Options",
                style = MaterialTheme.typography.headlineSmall,
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ExportMode.entries.forEachIndexed { index, mode ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = ExportMode.entries.size,
                        ),
                        onClick = toggleMode,
                        selected = mode == selectedMode,
                        label = { Text(mode.value) },
                    )
                }
            }
        }
    }
}
