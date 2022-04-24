package com.cassnyo.showpicker.data.repository

import com.cassnyo.showpicker.data.mapper.mapTvShowResponseToTvShow
import com.cassnyo.showpicker.data.network.TmdbApi
import com.cassnyo.showpicker.ui.model.TvShow

class TvShowRepository(
    private val tmdbApi: TmdbApi
) {
    // This property acts as a shared cache between TopRated and Detail.
    // Alternatives:
    // - Create a SharedViewModel
    // - Send the selected Tv Show's id from TopRated to Detail, and then look for its information on a cache (Room)
    var selectedTvShow: TvShow? = null

    suspend fun getTopRatedTvShows(page: Int): List<TvShow> {
        val response = tmdbApi.getTopRatedTvShows("en-US", page)
        return mapTvShowResponseToTvShow(response.results)
    }

    suspend fun getSimilarTvShows(tvShowId: Int, page: Int): List<TvShow> {
        val response = tmdbApi.getSimilarTvShows(tvShowId, "en-US", page)
        return mapTvShowResponseToTvShow(response.results)
    }

}