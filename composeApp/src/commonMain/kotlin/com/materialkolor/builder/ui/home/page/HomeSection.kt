package com.materialkolor.builder.ui.home.page

import com.materialkolor.builder.core.exportSupported
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

enum class HomeSection {
    Customize,
    Preview,
    Components,
    Themes,
    Palettes,
    Export;

    companion object {

        val All: PersistentList<HomeSection> =
            if (exportSupported) HomeSection.entries.toPersistentList()
            else (HomeSection.entries - Export).toPersistentList()

        val Compact: PersistentList<HomeSection> = (All - Preview).toPersistentList()
        val Expanded: PersistentList<HomeSection> = All
    }
}