package com.cassnyo.showpicker.ui.model

import java.time.LocalDate

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val contentUrl: String,
    val firstAirDate: LocalDate?,
    val popularity: Float,
    val voteCount: Int,
    val voteAverage: Float,
    val originCountry: List<String>,
    val originalLanguage: String
)