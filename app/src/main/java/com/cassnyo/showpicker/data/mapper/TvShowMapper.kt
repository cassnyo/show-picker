package com.cassnyo.showpicker.data.mapper

import com.cassnyo.showpicker.BuildConfig
import com.cassnyo.showpicker.data.network.model.TvShowResponse
import com.cassnyo.showpicker.ui.model.TvShow

fun TvShowResponse.mapToTvShow(): TvShow =
    TvShow(
        id,
        name,
        overview,
        mapPosterPathToUrl(posterPath),
        mapBackdropPathToUrl(backdropPath),
        createContentUrl(id),
        firstAirDate,
        popularity,
        voteCount,
        voteAverage,
        originCountry,
        originalLanguage
    )

private fun createContentUrl(id: Int) = "${BuildConfig.TMDB_CONTENT_URL}tv/$id"