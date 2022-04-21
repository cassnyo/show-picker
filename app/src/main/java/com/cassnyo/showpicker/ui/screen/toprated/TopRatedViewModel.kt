package com.cassnyo.showpicker.ui.screen.toprated

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _tvShows = MutableStateFlow<List<TvShow>>(emptyList())
    val tvShows: Flow<List<TvShow>> = _tvShows

    private val _viewMode = MutableStateFlow<ViewMode>(ViewMode.List)
    val viewMode: Flow<ViewMode> = _viewMode

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private var currentPage = 0

    init {
        loadTopRatedTvShows()
    }

    fun loadTopRatedTvShows() {
        _isLoading.value = true
        currentPage += 1
        viewModelScope.launch {
            val tvShows = tvShowRepository.getTopRatedTvShows(currentPage)
            _tvShows.value = _tvShows.value.toMutableList().apply {
                addAll(tvShows)
            }
            _isLoading.value = false
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