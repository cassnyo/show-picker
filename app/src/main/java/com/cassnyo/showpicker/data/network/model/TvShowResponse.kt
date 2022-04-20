package com.cassnyo.showpicker.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class TvShowResponse(
    val id: Int,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: LocalDate?,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    val popularity: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "original_language") val originalLanguage: String
)