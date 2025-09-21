package com.misenpai.anivault.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.misenpai.anivault.presentation.components.AnimeCard
import com.misenpai.anivault.presentation.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Search Bar
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search anime...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            ),
            singleLine = true
        )

        // Search Results
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val state = searchState) {
                is SearchState.Idle -> {
                    Text(
                        text = "Start searching for anime",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchState.Loading -> {
                    LoadingIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchState.Empty -> {
                    Text(
                        text = "No results found",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchState.Success -> {
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
                is SearchState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}