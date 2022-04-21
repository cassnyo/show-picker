package com.cassnyo.showpicker.data.repository

import com.cassnyo.showpicker.data.mapper.mapTvShowResponseToTvShow
import com.cassnyo.showpicker.data.network.TmdbApi
import com.cassnyo.showpicker.ui.model.TvShow

class TvShowRepository(
    private val tmdbApi: TmdbApi
) {

    suspend fun getTopRatedTvShows(page: Int): List<TvShow> {
        val response = tmdbApi.getTopRatedTvShows("en-US", page)
        return mapTvShowResponseToTvShow(response.results)
    }

}