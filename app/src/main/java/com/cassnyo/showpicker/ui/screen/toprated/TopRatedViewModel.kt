package com.cassnyo.showpicker.ui.screen.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cassnyo.showpicker.data.repository.TvShowRepository
import com.cassnyo.showpicker.ui.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
): ViewModel() {

    private val _tvShows = MutableStateFlow<PagingData<TvShow>>(PagingData.empty())
    val tvShows: Flow<PagingData<TvShow>> = _tvShows

    private val _viewMode = MutableStateFlow<ViewMode>(ViewMode.List)
    val viewMode: Flow<ViewMode> = _viewMode

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            tvShowRepository
                .getTopRatedTvShows()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _tvShows.value = pagingData
                    _isLoading.value = false
                }
        }
    }

    fun toggleViewMode(viewMode: ViewMode) {
        _viewMode.value = viewMode
    }

}

sealed class ViewMode {
    object List : ViewMode()
    object Grid : ViewMode()
}