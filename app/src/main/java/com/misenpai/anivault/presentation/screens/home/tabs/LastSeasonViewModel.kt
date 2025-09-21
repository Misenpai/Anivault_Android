package com.misenpai.anivault.presentation.screens.home.tabs

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.constants.Constants
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LastSeasonViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeasonUiState>(SeasonUiState.Loading)
    val uiState: StateFlow<SeasonUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val animeList = mutableListOf<Anime>()

    init {
        loadLastSeasonAnime()
    }

    fun loadLastSeasonAnime() {
        viewModelScope.launch {
            _uiState.value = SeasonUiState.Loading

            val (year, season) = getPreviousSeasonInfo()

            when (val result = animeRepository.getSeasonAnime(year, season, currentPage)) {
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

    private fun getPreviousSeasonInfo(): Pair<Int, String> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val currentSeason = when (month) {
            in 0..2 -> Constants.AnimeSeason.WINTER
            in 3..5 -> Constants.AnimeSeason.SPRING
            in 6..8 -> Constants.AnimeSeason.SUMMER
            else -> Constants.AnimeSeason.FALL
        }

        return when (currentSeason) {
            Constants.AnimeSeason.WINTER -> Pair(year - 1, Constants.AnimeSeason.FALL)
            Constants.AnimeSeason.SPRING -> Pair(year, Constants.AnimeSeason.WINTER)
            Constants.AnimeSeason.SUMMER -> Pair(year, Constants.AnimeSeason.SPRING)
            else -> Pair(year, Constants.AnimeSeason.SUMMER)
        }
    }

    fun loadMore() {
        currentPage++
        loadLastSeasonAnime()
    }

    fun refresh() {
        currentPage = 1
        animeList.clear()
        loadLastSeasonAnime()
    }
}