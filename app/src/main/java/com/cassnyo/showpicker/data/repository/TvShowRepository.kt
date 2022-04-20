package com.cassnyo.showpicker.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cassnyo.showpicker.data.paging.TopRatedTvShowPagingSource
import com.cassnyo.showpicker.ui.model.TvShow
import kotlinx.coroutines.flow.Flow

class TvShowRepository(
    private val topRatedTvShowPagingSource: TopRatedTvShowPagingSource
) {

    fun getTopRatedTvShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { topRatedTvShowPagingSource }
        ).flow
    }

}