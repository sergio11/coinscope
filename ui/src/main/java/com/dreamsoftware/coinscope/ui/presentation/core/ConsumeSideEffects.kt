package com.dreamsoftware.coinscope.ui.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@Composable
internal fun <SE: SideEffect, VM: SupportViewModel<*, SE>>ConsumeSideEffects(
    viewModel: VM,
    lifecycle: Lifecycle,
    onSideEffectFired: (SE) -> Unit
) {
    RepeatOnStart(viewModel, lifecycle) {
        sideEffectWithReplay.collectLatest { event ->
            onSideEffectFired(event)
        }
    }
    RepeatOnStart(viewModel, lifecycle) {
        sideEffectWithoutReplay.collectLatest { event ->
            onSideEffectFired(event)
        }
    }
}

@Composable
private fun <SE: SideEffect, VM: SupportViewModel<*, SE>> RepeatOnStart(
    viewModel: VM,
    lifecycle: Lifecycle,
    block: suspend VM.() -> Unit
) {
    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.block()
            }
        }
    }
}