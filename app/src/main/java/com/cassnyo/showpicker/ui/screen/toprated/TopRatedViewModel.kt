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
import timber.log.Timber
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

    var uiState by mutableStateOf(UiState())
        private set

    private var nextPage = 1
    private var totalPages: Int? = null

    init {
        loadTopRatedTvShows()
    }

    fun loadTopRatedTvShows() {
        viewModelScope.launch {
            val maxPage = totalPages
            if (maxPage != null && nextPage > maxPage) {
                Timber.d("Reached last page")
                return@launch
            }

            uiState = uiState.copy(isLoading = true)

            val pagedResult = tvShowRepository.getTopRatedTvShows(nextPage)
            nextPage = pagedResult.page + 1
            totalPages = pagedResult.totalPages
            val tvShows = uiState.tvShows.toMutableList().apply {
                addAll(pagedResult.data)
            }

            uiState = uiState.copy(
                tvShows = tvShows,
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

    fun onTvShowClick(selectedTvShow: TvShow) {
        tvShowRepository.selectedTvShow = selectedTvShow
    }

}

sealed class ViewMode {
    object List : ViewMode()
    object Grid : ViewMode()
}