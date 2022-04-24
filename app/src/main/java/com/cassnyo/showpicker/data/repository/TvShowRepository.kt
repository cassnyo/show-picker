package com.cassnyo.showpicker.data.repository

import com.cassnyo.showpicker.data.mapper.mapToPagedResult
import com.cassnyo.showpicker.data.mapper.mapToTvShow
import com.cassnyo.showpicker.data.network.TmdbApi
import com.cassnyo.showpicker.ui.model.PagedResult
import com.cassnyo.showpicker.ui.model.TvShow
import timber.log.Timber

class TvShowRepository(
    private val tmdbApi: TmdbApi
) {
    // This property acts as a shared cache between TopRated and Detail.
    // Alternatives:
    // - Create a SharedViewModel
    // - Send the selected Tv Show's id from TopRated to Detail, and then look for its information on a cache (Room)
    var selectedTvShow: TvShow? = null

    suspend fun getTopRatedTvShows(page: Int): PagedResult<TvShow> {
        Timber.d("Loading top rated. Page: $page")
        val response = tmdbApi.getTopRatedTvShows("en-US", page)
        return response.mapToPagedResult { it.mapToTvShow() }
    }

    suspend fun getSimilarTvShows(tvShowId: Int, page: Int): PagedResult<TvShow> {
        Timber.d("Loading similar [$tvShowId]. Page: $page")
        val response = tmdbApi.getSimilarTvShows(tvShowId, "en-US", page)
        return response.mapToPagedResult { it.mapToTvShow() }
    }

}