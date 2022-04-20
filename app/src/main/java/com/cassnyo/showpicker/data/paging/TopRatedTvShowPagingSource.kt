package com.cassnyo.showpicker.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cassnyo.showpicker.data.mapper.mapTvShowResponseToTvShow
import com.cassnyo.showpicker.data.network.TmdbApi
import com.cassnyo.showpicker.ui.model.TvShow

class TopRatedTvShowPagingSource(
    private val tmdbApiService: TmdbApi
): PagingSource<Int, TvShow>() {

    private var totalPages: Int? = null

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val currentPage = params.key ?: 1
        return try {
            // FIXME Use dynamic language selected by user
            val response = tmdbApiService.getTopRatedTvShows("en-US", currentPage)
            totalPages = response.totalPages
            LoadResult.Page(
                data = mapTvShowResponseToTvShow(response.results),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage == totalPages) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}