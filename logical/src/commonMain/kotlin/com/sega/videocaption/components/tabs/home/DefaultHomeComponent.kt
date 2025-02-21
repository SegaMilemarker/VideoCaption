package com.sega.videocaption.components.tabs.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.getValue
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.distinctUntilChanged
import com.badoo.reaktive.observable.observableInterval
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.scheduler.Scheduler
import com.badoo.reaktive.subject.behavior.BehaviorObservable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.sega.videocaption.components.tabs.TabsComponent.AppTab.HOME
import com.sega.videocaption.components.tabs.home.HomeComponent.Model
import com.sega.videocaption.network.base.ServiceLocator
import com.sega.videocaption.utils.asObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

class DefaultHomeComponent(
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private var movieService = ServiceLocator.movieService

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _data =
        MutableValue(
            Model()
        )

    override val data: Value<Model> = _data

    init {
        data.map { it.currentTab }
            .asObservable()
            .distinctUntilChanged()
            .subscribe { value ->
                fetchChangedMovies(value)
            }
    }

    private fun fetchChangedMovies(category: HomeMovieCategory) {
        scope.launch {
            val result = movieService.getListMovie(category)
            result.onSuccess { response ->
                println("Received movie changes: $response")
                println("Received movie changes size: ${response.size}")
                // Handle the successful response, e.g., update UI or state
            }.onFailure { exception ->
                println("Error fetching movie changes: ${exception.message}")
                // Handle the error, e.g., show error message
            }
        }
    }
}