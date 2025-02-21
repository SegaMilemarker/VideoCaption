package com.sega.videocaption.network.service.movie

import com.sega.videocaption.components.tabs.home.HomeMovieCategory
import com.sega.videocaption.network.base.NetworkTarget
import io.ktor.http.*
import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern


sealed class MovieTarget : NetworkTarget {

    val apiKey = "4d0830d750f5ae621325e0907baa5d3b"

    data object GetChangedMovies : MovieTarget() {
        override val baseURL = "https://api.themoviedb.org/3"
        override val path = "/movie/changes"
        override val method = HttpMethod.Get
        override val params: Map<String, String> = mapOf("api_key" to apiKey, "start_date" to getCurrentDate(), "end_date" to getCurrentDate())
        override val headers: Map<String, String>? = null

        @OptIn(FormatStringsInDatetimeFormats::class)
        private fun getCurrentDate(): String {
            val dateTimeFormat = LocalDateTime.Format {
                byUnicodePattern("yyyy-MM-dd")
            }
            val currentMoment: Instant = Clock.System.now()
            val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
            return dateTimeFormat.format(datetimeInUtc)
        }
    }

    data class GetListDetails(val listId: String) : MovieTarget() {
        override val baseURL = "https://api.themoviedb.org/3"
        override val path = "/list/$listId"
        override val method = HttpMethod.Get
        override val params: Map<String, String> = mapOf("api_key" to apiKey)
        override val headers: Map<String, String>? = null
    }

    data class GetMovieDetails(val movieId: Int) : MovieTarget() {
        override val baseURL = "https://api.themoviedb.org/3"
        override val path = "/movie/$movieId"
        override val method = HttpMethod.Get
        override val params: Map<String, String> = mapOf("api_key" to apiKey)
        override val headers: Map<String, String>? = null
    }

    data class GetListMovie(val category: HomeMovieCategory) : MovieTarget() {
        override val baseURL = "https://api.themoviedb.org/3"
        override val path = "/movie/${category.title}"
        override val method = HttpMethod.Get
        override val params: Map<String, String> = mapOf("api_key" to apiKey)
        override val headers: Map<String, String>? = null
    }

}