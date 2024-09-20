package com.materialkolor.builder.ui.ktx

// TODO: This is copied from voyager, because they don't support wasm yet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

@Composable
inline fun <reified T : ScreenModel> Screen.koinScreenModel(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val st = remember(parameters) { parameters }
    val tag = remember(qualifier, scope) { qualifier?.value }
    return rememberScreenModel(tag = tag) {
        scope.get(qualifier, st)
    }
}

@Composable
inline fun <reified T : ScreenModel> Navigator.koinNavigatorScreenModel(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val st = remember(parameters) { parameters }
    val tag = remember(qualifier, scope) { qualifier?.value }
    return rememberNavigatorScreenModel(tag = tag) {
        scope.get(qualifier, st)
    }
}