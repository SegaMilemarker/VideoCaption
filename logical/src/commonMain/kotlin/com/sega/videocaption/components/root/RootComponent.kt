package com.sega.videocaption.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.sega.videocaption.components.splash.SplashComponent
import com.sega.videocaption.components.tabs.TabsComponent


interface RootComponent : BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()
    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class SplashChild(val component: SplashComponent) : Child() // /tabs
        class TabsChild(val component: TabsComponent) : Child() // /tabs
    }
}