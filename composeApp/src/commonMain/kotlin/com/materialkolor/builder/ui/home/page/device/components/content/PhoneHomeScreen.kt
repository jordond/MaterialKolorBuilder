package com.materialkolor.builder.ui.home.page.device.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Stable
data class CardDetails(
    val room: String,
    val options: PersistentList<CardOption>,
)

@Stable
data class CardOption(
    val title: String,
    val description: String,
    val selected: Boolean = true,
)

private val Options = persistentListOf(
    CardDetails(
        room = "Living Room",
        options = persistentListOf(
            CardOption(title = "Water", description = "hoya australis"),
            CardOption(title = "Feed", description = "monstera siltepecana"),
        ),
    ),
    CardDetails(
        room = "Kitchen",
        options = persistentListOf(
            CardOption(title = "Water", description = "pilea peperomioides"),
            CardOption(title = "Water", description = "hoya australis"),
        ),
    ),
    CardDetails(
        room = "Bedroom",
        options = persistentListOf(
            CardOption(title = "Feed", description = "monstera siltepecana"),
            CardOption(title = "Water", description = "philodendron brandi"),
        ),
    ),
)

@Composable
fun PhoneHomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize(),
    ) {

        Title(modifier = Modifier.padding(top = 24.dp))
        Spacer(modifier = Modifier.height(32.dp))
        DailyTip()
        Spacer(modifier = Modifier.height(16.dp))
        List()
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = "Today",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.weight(1f)
        )

        FilledIconButton(
            onClick = {},
            modifier = Modifier
                .width(48.dp)
                .height(24.dp)
        ) {
            Icon(Icons.Filled.Person, contentDescription = "Person")
        }
    }
}

@Composable
private fun DailyTip(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 8.dp, end = 12.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Lightbulb,
                contentDescription = "Tip",
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "During the winter your plants slow down and need less water",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun List(
    modifier: Modifier = Modifier,
) {
    var options by remember { mutableStateOf(Options) }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        options.forEach { details ->
            ListItem(
                details = details,
                update = { option ->
                    val index = options.indexOfFirst { it.room == details.room }
                    val updated = details.options
                        .map { if (it.description == option.description) option else it }
                        .toPersistentList()

                    options = options.set(index, details.copy(options = updated))
                },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ListItem(
    details: CardDetails,
    update: (CardOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = details.room,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            details.options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .toggleable(
                            value = option.selected,
                            onValueChange = {
                                update(option.copy(selected = it))
                            },
                            role = Role.Checkbox
                        )
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Checkbox(
                        checked = option.selected,
                        onCheckedChange = null
                    )

                    Column {
                        Text(
                            text = option.title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = option.description,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }
    }
}