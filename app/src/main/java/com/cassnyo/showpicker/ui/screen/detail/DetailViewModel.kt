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
import timber.log.Timber
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

    private var nextPage = 1
    private var totalPages: Int? = null

    init {
        loadSimilarTvShows()
    }

    fun loadSimilarTvShows() {
        viewModelScope.launch {
            val maxPage = totalPages
            if (maxPage != null && nextPage > maxPage) {
                Timber.d("Reached last page")
                return@launch
            }

            try {
                val pagedResult = tvShowRepository.getSimilarTvShows(selectedTvShow.id, nextPage)
                nextPage = pagedResult.page + 1
                totalPages = pagedResult.totalPages

                val tvShows = uiState.tvShows.toMutableList().apply {
                    addAll(pagedResult.data)
                }

                uiState = uiState.copy(tvShows = tvShows)
            } catch (e: Exception) {
                Timber.e("Error loading similar: ${e.message}")
            }
        }
    }

}