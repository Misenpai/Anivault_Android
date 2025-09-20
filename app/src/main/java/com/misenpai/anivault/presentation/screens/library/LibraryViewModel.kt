package com.misenpai.anivault.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.data.local.preferences.UserPreferences
import com.misenpai.anivault.domain.model.AnimeStatus
import com.misenpai.anivault.domain.model.WatchStatus
import com.misenpai.anivault.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(WatchStatus.WATCHING)
    val selectedTab: StateFlow<WatchStatus> = _selectedTab.asStateFlow()

    private val _libraryState = MutableStateFlow<LibraryState>(LibraryState.Loading)
    val libraryState: StateFlow<LibraryState> = _libraryState.asStateFlow()

    init {
        loadUserLibrary()
    }

    fun selectTab(status: WatchStatus) {
        _selectedTab.value = status
    }

    private fun loadUserLibrary() {
        viewModelScope.launch {
            val userId = userPreferences.getUserId()
            if (userId == -1) {
                _libraryState.value = LibraryState.Error("User not logged in")
                return@launch
            }

            animeRepository.getUserAnimeList(userId)
                .combine(selectedTab) { animeList, selectedStatus ->
                    animeList.filter { it.status == selectedStatus }
                }
                .collect { filteredList ->
                    if (filteredList.isEmpty()) {
                        _libraryState.value = LibraryState.Empty
                    } else {
                        _libraryState.value = LibraryState.Success(filteredList)
                    }
                }
        }
    }
}

sealed class LibraryState {
    object Loading : LibraryState()
    object Empty : LibraryState()
    data class Success(val animeList: List<AnimeStatus>) : LibraryState()
    data class Error(val message: String) : LibraryState()
}