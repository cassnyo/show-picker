package com.cassnyo.showpicker.ui.screen.detail

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
class DetailViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
): ViewModel() {

    data class UiState(
        val tvShows: List<TvShow> = emptyList()
    )

    private var selectedTvShow = tvShowRepository.selectedTvShow!!
    var uiState by mutableStateOf(UiState(listOf(selectedTvShow)))
        private set

    private var currentPage = 0

    init {
        loadSimilarTvShows()
    }

    fun loadSimilarTvShows() {
        currentPage += 1
        viewModelScope.launch {
            val result = tvShowRepository.getSimilarTvShows(selectedTvShow.id, currentPage)
            val currentTvShows = uiState.tvShows.toMutableList()
            uiState = uiState.copy(
                tvShows = currentTvShows.apply { addAll(result) }
            )
        }
    }

}