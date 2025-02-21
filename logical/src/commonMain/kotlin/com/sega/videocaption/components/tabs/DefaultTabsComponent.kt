package com.sega.videocaption.components.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.sega.videocaption.components.tabs.TabsComponent.AppTab
import com.sega.videocaption.components.tabs.feeds.DefaultFeedsComponent
import com.sega.videocaption.components.tabs.home.DefaultHomeComponent
import com.sega.videocaption.components.tabs.settings.DefaultSettingsComponent
import kotlinx.serialization.Serializable

class DefaultTabsComponent(
    componentContext: ComponentContext,
    tabIndex: Int
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.fromIndex(tabIndex),
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = _stack

    override fun onTabClicked(tab: AppTab) {
        val config = when (tab) {
            AppTab.HOME -> Config.Home
            AppTab.FEEDS -> Config.Feeds
            AppTab.SETTINGS -> Config.Settings
        }

        navigation.bringToFront(config)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TabsComponent.Child =
        when (config) {
            is Config.Home -> TabsComponent.Child.HomeChild(DefaultHomeComponent(componentContext))
            is Config.Feeds -> TabsComponent.Child.FeedsChild(DefaultFeedsComponent(componentContext))
            is Config.Settings -> TabsComponent.Child.SettingsChild(
                DefaultSettingsComponent(
                    componentContext
                )
            )
        }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Feeds : Config()

        @Serializable
        data object Settings : Config()

        companion object {
            fun fromIndex(index: Int): Config {
                return when (index) {
                    0 -> Home
                    1 -> Feeds
                    2 -> Settings
                    else -> throw IllegalArgumentException("Invalid tab index")
                }
            }
        }
    }
}