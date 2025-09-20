package com.misenpai.anivault.presentation.screens.home.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.usecase.anime.GetCurrentSeasonAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThisSeasonViewModel @Inject constructor(
    private val getCurrentSeasonAnimeUseCase: GetCurrentSeasonAnimeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeasonUiState>(SeasonUiState.Loading)
    val uiState: StateFlow<SeasonUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val animeList = mutableListOf<Anime>()

    init {
        loadThisSeasonAnime()
    }

    fun loadThisSeasonAnime() {
        viewModelScope.launch {
            _uiState.value = SeasonUiState.Loading

            when (val result = getCurrentSeasonAnimeUseCase(currentPage)) {
                is Resource.Success -> {
                    result.data?.let { anime ->
                        if (anime.isEmpty() && animeList.isEmpty()) {
                            _uiState.value = SeasonUiState.Empty
                        } else {
                            animeList.addAll(anime)
                            _uiState.value = SeasonUiState.Success(animeList.toList())
                        }
                    }
                }
                is Resource.Error -> {
                    _uiState.value = SeasonUiState.Error(
                        result.message ?: "Failed to load anime"
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = SeasonUiState.Loading
                }
            }
        }
    }

    fun loadMore() {
        currentPage++
        loadThisSeasonAnime()
    }

    fun refresh() {
        currentPage = 1
        animeList.clear()
        loadThisSeasonAnime()
    }
}

sealed class SeasonUiState {
    object Loading : SeasonUiState()
    object Empty : SeasonUiState()
    data class Success(val animeList: List<Anime>) : SeasonUiState()
    data class Error(val message: String) : SeasonUiState()
}