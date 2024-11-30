package com.dreamsoftware.coinscope.ui.presentation.core

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun <UI: UiState<UI>, SE: SideEffect, VM: SupportViewModel<UI, SE>> SupportScreen(
    viewModel: VM,
    onInitialUiState: () -> UI,
    onInit: VM.() -> Unit = {},
    onBackPressed: () -> Unit = {},
    onSideEffect: VM.(SE) -> Unit = {},
    onResume: VM.() -> Unit = {},
    onPause: VM.() -> Unit = {},
    content: @Composable VM.(uiState: UI) -> Unit
) {
    BackHandler(onBack = onBackPressed)
    with(viewModel) {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        ConsumeSideEffects(
            lifecycle = lifecycle,
            viewModel = viewModel,
            onSideEffectFired = {
                onSideEffect(it)
            }
        )
        LifecycleResumeEffect(Unit) {
            onResume()
            onPauseOrDispose {
                onPause()
            }
        }
        val uiState by produceUiState(
            initialState = onInitialUiState(),
            lifecycle = lifecycle,
            viewModel = viewModel
        )
        LaunchedEffect(key1 = lifecycle, key2 = viewModel) {
            onInit()
        }
        content(uiState)
    }
}