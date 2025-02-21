package com.sega.videocaption.network.service.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieChangesResponse(
    val results: List<MovieChange>
)

@Serializable
data class MovieChange(
    val id: Int,
    val adult: Boolean
)

@Serializable
data class MovieGenre(
    val id: Int,
    val name: String
)


@Serializable
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("release_date") val releaseDate: String,
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genres") val genres: List<MovieGenre> = emptyList(),
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class MovieListResponse(
    val dates: Dates? = null,
    val page: Int,
    val results: List<MovieDetail>
)

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)