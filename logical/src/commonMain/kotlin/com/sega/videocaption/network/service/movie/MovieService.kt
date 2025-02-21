package com.sega.videocaption.network.service.movie

import com.sega.videocaption.components.tabs.home.HomeMovieCategory
import com.sega.videocaption.network.base.NetworkClient
import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class MovieService(private val networkClient: NetworkClient) {

    suspend fun getChangedMovies(): Result<List<MovieDetail>> = withContext(Dispatchers.IO) {
        val changedMoviesResult = networkClient.request<MovieChangesResponse>(MovieTarget.GetChangedMovies)
        if (changedMoviesResult.isSuccess) {
            val changedMovies = changedMoviesResult.getOrNull()?.results ?: emptyList()
            val movieDetails = fetchMovieDetailsConcurrently(changedMovies)
            Result.success(movieDetails)
        } else {
            Result.failure(changedMoviesResult.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }

    private suspend fun fetchMovieDetailsConcurrently(changedMovies: List<MovieChange>): List<MovieDetail> = coroutineScope {
        val deferredDetails = changedMovies.map { movieChange ->
            async {
                val detailResult = networkClient.request<MovieDetail>(MovieTarget.GetMovieDetails(movieChange.id))
                detailResult.getOrNull()
            }
        }
        deferredDetails.mapNotNull { it.await() }
    }

    suspend fun getListMovie(category: HomeMovieCategory): Result<List<MovieDetail>> = withContext(Dispatchers.IO) {
        val nowPlayingResult = networkClient.request<MovieListResponse>(MovieTarget.GetListMovie(category))
        if (nowPlayingResult.isSuccess) {
            val result = nowPlayingResult.getOrNull() ?: throw Exception("Failed to fetch now playing movies")
            Result.success(result.results)
        } else {
            Result.failure(nowPlayingResult.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}
