package com.cassnyo.showpicker.data.di

import com.cassnyo.showpicker.data.network.TmdbApi
import com.cassnyo.showpicker.data.paging.TopRatedTvShowPagingSource
import com.cassnyo.showpicker.data.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTopRatedTvShowPagingSource(
        tmdbApi: TmdbApi
    ): TopRatedTvShowPagingSource {
        return TopRatedTvShowPagingSource(tmdbApi)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        topRatedTvShowPagingSource: TopRatedTvShowPagingSource
    ): TvShowRepository {
        return TvShowRepository(topRatedTvShowPagingSource)
    }

}