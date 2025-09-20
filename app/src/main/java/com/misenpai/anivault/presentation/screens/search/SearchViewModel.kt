package com.misenpai.anivault.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.usecase.anime.SearchAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        searchQuery = query
        searchJob?.cancel()

        if (query.isBlank()) {
            _searchState.value = SearchState.Idle
            return
        }

        searchJob = viewModelScope.launch {
            delay(500) // Debounce
            searchAnime(query)
        }
    }

    private suspend fun searchAnime(query: String) {
        _searchState.value = SearchState.Loading

        when (val result = searchAnimeUseCase(query)) {
            is Resource.Success -> {
                result.data?.let { animeList ->
                    if (animeList.isEmpty()) {
                        _searchState.value = SearchState.Empty
                    } else {
                        _searchState.value = SearchState.Success(animeList)
                    }
                }
            }
            is Resource.Error -> {
                _searchState.value = SearchState.Error(
                    result.message ?: "Search failed"
                )
            }
            is Resource.Loading -> {
                _searchState.value = SearchState.Loading
            }
        }
    }
}

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    object Empty : SearchState()
    data class Success(val animeList: List<Anime>) : SearchState()
    data class Error(val message: String) : SearchState()
}