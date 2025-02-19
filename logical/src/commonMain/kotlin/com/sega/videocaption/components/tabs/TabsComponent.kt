package com.sega.videocaption.components.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.sega.videocaption.components.tabs.feeds.FeedsComponent
import com.sega.videocaption.components.tabs.home.HomeComponent
import com.sega.videocaption.components.tabs.settings.SettingComponent

interface TabsComponent  {

    val stack: Value<ChildStack<*, Child>>

    fun onTabClicked(tab: AppTab)

    enum class AppTab(val index: Int) {
        HOME(0),
        FEEDS(1),
        SETTINGS(2);

        companion object {
            fun fromIndex(index: Int): AppTab {
                return entries.firstOrNull { it.index == index }
                    ?: throw IllegalArgumentException("Invalid tab index")
            }
        }
    }

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class FeedsChild(val component: FeedsComponent) : Child()
        class SettingsChild(val component: SettingComponent) : Child()

        val index: Int
            get() = when (this) {
                is HomeChild -> AppTab.HOME.index
                is FeedsChild -> AppTab.FEEDS.index
                is SettingsChild -> AppTab.SETTINGS.index
            }
    }
}