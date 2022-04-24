package com.cassnyo.showpicker.data.mapper

import com.cassnyo.showpicker.BuildConfig
import com.cassnyo.showpicker.data.network.model.TvShowResponse
import com.cassnyo.showpicker.ui.model.TvShow

fun mapTvShowResponseToTvShow(tvShowList: List<TvShowResponse>): List<TvShow> {
    return tvShowList.map { tvShowResponse ->
        TvShow(
            tvShowResponse.id,
            tvShowResponse.name,
            tvShowResponse.overview,
            mapPosterPathToUrl(tvShowResponse.posterPath),
            mapBackdropPathToUrl(tvShowResponse.backdropPath),
            createContentUrl(tvShowResponse.id),
            tvShowResponse.firstAirDate,
            tvShowResponse.popularity,
            tvShowResponse.voteCount,
            tvShowResponse.voteAverage,
            tvShowResponse.originCountry,
            tvShowResponse.originalLanguage
        )
    }
}

private fun createContentUrl(id: Int) = "${BuildConfig.TMDB_CONTENT_URL}tv/$id"