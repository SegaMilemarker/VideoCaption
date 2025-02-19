package com.sega.videocaption.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.sega.videocaption.Url
import com.sega.videocaption.components.root.DefaultRootComponent.Config.Splash
import com.sega.videocaption.components.root.DefaultRootComponent.Config.Tabs
import com.sega.videocaption.components.root.RootComponent.Child
import com.sega.videocaption.components.root.RootComponent.Child.SplashChild
import com.sega.videocaption.components.root.RootComponent.Child.TabsChild
import com.sega.videocaption.components.splash.DefaultSplashComponent
import com.sega.videocaption.components.tabs.DefaultTabsComponent
import com.sega.videocaption.consumePathSegment
import com.sega.videocaption.pathSegmentOf
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    deepLinkUrl: Url? = null,
) : RootComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialStack = { getInitialStack(deepLinkUrl) },
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<*, Child>> = _stack

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Splash ->
                SplashChild(
                    DefaultSplashComponent(
                        componentContext = componentContext,
                        goToApp = {
                            nav.bringToFront(Config.Tabs(0))
                        }
                    )
                )

            is Tabs ->
                TabsChild(
                    DefaultTabsComponent(
                        componentContext = componentContext,
                        tabIndex = config.tabIndex,
                    )
                )
        }

    override fun onBackClicked() {
        nav.pop()
    }

    override fun onBackClicked(toIndex: Int) {
        nav.popTo(index = toIndex)
    }

    private fun getInitialStack(deepLinkUrl: Url?): List<Config> {
        val (path, childUrl) = deepLinkUrl?.consumePathSegment() ?: return listOf(Splash())

        return when (path) {
            pathSegmentOf<Splash>() -> listOf(Splash())
            pathSegmentOf<Tabs>() -> listOf(Tabs(tabIndex = 0))
            else -> { listOf(Splash()) }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data class Splash(val deepLinkUrl: Url? = null) : Config

        @Serializable
        data class Tabs(val tabIndex: Int) : Config
    }
}