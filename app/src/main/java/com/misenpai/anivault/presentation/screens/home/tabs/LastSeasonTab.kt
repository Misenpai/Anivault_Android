package com.misenpai.anivault.presentation.screens.home.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.misenpai.anivault.presentation.components.AnimeCard
import com.misenpai.anivault.presentation.components.LoadingIndicator

@Composable
fun LastSeasonTab(
    viewModel: LastSeasonViewModel = hiltViewModel()
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
                    onRetry = { viewModel.loadLastSeasonAnime() },
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

@Composable
fun ErrorMessage(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No anime found",
            color = Color.Gray,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try refreshing or check back later",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}