package com.cassnyo.showpicker.data.network

import com.cassnyo.showpicker.data.network.model.PagedResponse
import com.cassnyo.showpicker.data.network.model.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("language") language: String,
        @Query("page") page: Int
    ): PagedResponse<TvShowResponse>

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PagedResponse<TvShowResponse>

}