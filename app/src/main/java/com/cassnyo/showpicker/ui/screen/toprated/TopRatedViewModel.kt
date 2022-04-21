package com.cassnyo.showpicker.ui.screen.toprated

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassnyo.showpicker.data.repository.TvShowRepository
import com.cassnyo.showpicker.ui.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
): ViewModel() {

    data class UiState(
        val tvShows: List<TvShow> = emptyList(),
        val isLoading: Boolean = false,
        val viewMode: ViewMode = ViewMode.List
    )

    private var currentPage = 0
    var uiState by mutableStateOf(UiState())
        private set

    init {
        loadTopRatedTvShows()
    }

    fun loadTopRatedTvShows() {
        currentPage += 1
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val tvShows = tvShowRepository.getTopRatedTvShows(currentPage)
            val currentTvShows = uiState.tvShows.toMutableList()
            uiState = uiState.copy(
                tvShows = currentTvShows.apply { addAll(tvShows) },
                isLoading = false
            )
        }
    }

    fun toggleViewMode(viewMode: ViewMode) {
        // Not allowed while loading
        if (uiState.isLoading) return
        uiState = uiState.copy(
            viewMode = viewMode
        )
    }

}

sealed class ViewMode {
    object List : ViewMode()
    object Grid : ViewMode()
}