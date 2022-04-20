package com.cassnyo.showpicker.data.mapper

fun mapPosterPathToUrl(posterPath: String?): String? {
    return posterPath?.let { "https://image.tmdb.org/t/p/w342$it" }
}

fun mapBackdropPathToUrl(backdropPath: String?): String? {
    return backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" }
}