package com.materialkolor.builder.ui.home.export

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.materialkolor.builder.export.ExportFile
import com.materialkolor.builder.ui.components.code.CodeTextView
import com.materialkolor.builder.ui.ktx.debugBorder
import com.materialkolor.builder.ui.theme.LocalThemeIsDark
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes
import kotlinx.collections.immutable.PersistentList

@Composable
fun FileListContainer(
    selected: ExportFile,
    files: PersistentList<ExportFile>,
    onSelected: (ExportFile) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(selected, files) {
        if (selected.name !in files.map { it.name }) {
            Logger.d { "Selected file ${selected.name} not in list, selecting first file" }
            onSelected(files.first())
        }
    }

    OutlinedCard(
        modifier = modifier,
    ) {
        Row {
            files.forEach { file ->
                Tab(
                    file = file,
                    isSelected = selected.name == file.name,
                    onClick = { onSelected(file) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Box(modifier = Modifier) {
            val isDark by LocalThemeIsDark.current
            val highlights by remember(files, selected) {
                mutableStateOf(
                    Highlights
                        .Builder()
                        .code(selected.content)
                        .language(selected.language)
                        .theme(SyntaxThemes.atom(isDark))
                        .build(),
                )
            }

            Card(
                onClick = onClick,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                CodeTextView(
                    highlights = highlights,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun Tab(
    file: ExportFile,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) CardDefaults.outlinedCardColors().containerColor
        else MaterialTheme.colorScheme.surfaceVariant,
    )

    val contentColor by animateColorAsState(
        if (isSelected) contentColorFor(CardDefaults.outlinedCardColors().contentColor)
        else contentColorFor(MaterialTheme.colorScheme.onSurfaceVariant),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(backgroundColor)
            .clickable(enabled = !isSelected, onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .widthIn(max = 200.dp),
    ) {
        Text(
            text = file.name,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Thin,
        )
    }
}
