package com.misenpai.anivault.presentation.screens.home.tabs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.misenpai.anivault.presentation.components.AnimeCard
import com.misenpai.anivault.presentation.components.LoadingIndicator

@Composable
fun ThisSeasonTab(
    viewModel: ThisSeasonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = uiState) {
            is SeasonUiState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is SeasonUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.animeList) { anime ->
                        AnimeCard(
                            anime = anime,
                            onClick = {
                                // Navigate to details
                            }
                        )
                    }
                }
            }
            is SeasonUiState.Error -> {
                ErrorMessage(
                    message = state.message,
                    onRetry = { viewModel.loadThisSeasonAnime() },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            SeasonUiState.Empty -> {
                EmptyState(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}