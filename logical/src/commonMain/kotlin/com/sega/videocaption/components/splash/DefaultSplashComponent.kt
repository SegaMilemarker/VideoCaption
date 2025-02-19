package com.sega.videocaption.components.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.sega.videocaption.components.root.DefaultRootComponent
import kotlinx.coroutines.*


class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val goToApp: () -> Unit,
) : SplashComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    init {
        scope.launch {
            delay(5000L)
            goToApp()
        }
    }
}