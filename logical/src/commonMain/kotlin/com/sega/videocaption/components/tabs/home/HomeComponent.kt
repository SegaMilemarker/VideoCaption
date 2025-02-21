package com.sega.videocaption.components.tabs.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.sega.videocaption.components.tabs.TabsComponent.AppTab
import com.sega.videocaption.components.tabs.TabsComponent.Child.*
import com.sega.videocaption.network.service.movie.MovieDetail
import com.sega.videocaption.network.service.movie.MovieGenre
import com.sega.videocaption.network.service.movie.MovieTarget
import kotlinx.serialization.Serializable

interface HomeComponent : BackHandlerOwner {

    val data: Value<Model>

    @Serializable
    data class Model(
        val categoryTab: List<HomeMovieCategory> = HomeMovieCategory.entries,
        val currentTab: HomeMovieCategory = HomeMovieCategory.POPULAR,

        val listMovie: List<MovieDetail> = emptyList()
    )
}


enum class HomeMovieCategory {
    NOWPLAYING, POPULAR, TOPRATED, UPCOMING;

    val title: String
        get() = when (this) {
            NOWPLAYING -> "now_playing"
            POPULAR -> "popular"
            TOPRATED -> "top_rated"
            UPCOMING -> "upcoming"
        }
}