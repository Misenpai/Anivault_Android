package com.misenpai.anivault.presentation.screens.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.misenpai.anivault.domain.model.WatchStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val libraryState by viewModel.libraryState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    val tabs = listOf(
        WatchStatus.WATCHING to "Watching",
        WatchStatus.COMPLETED to "Completed",
        WatchStatus.DROPPED to "Dropped",
        WatchStatus.PLAN_TO_WATCH to "Plan to Watch"
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header
        Text(
            text = "My Library",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Black,
            contentColor = Color.White
        ) {
            tabs.forEachIndexed { index, (status, title) ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        viewModel.selectTab(status)
                    }
                )
            }
        }

        // Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val status = tabs[page].first
            LibraryTabContent(
                status = status,
                libraryState = libraryState
            )
        }
    }
}

@Composable
fun LibraryTabContent(
    status: WatchStatus,
    libraryState: LibraryState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        when (libraryState) {
            is LibraryState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is LibraryState.Empty -> {
                Text(
                    text = "No anime in this list",
                    color = Color.Gray
                )
            }
            is LibraryState.Success -> {
                // Display anime list
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(libraryState.animeList.size) { index ->
                        val anime = libraryState.animeList[index]
                        AnimeStatusCard(anime = anime)
                    }
                }
            }
            is LibraryState.Error -> {
                Text(
                    text = libraryState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AnimeStatusCard(anime: com.misenpai.anivault.domain.model.AnimeStatus) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Anime details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = anime.animeName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "Episodes: ${anime.totalWatchedEpisodes}/${anime.totalEpisodes}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                LinearProgressIndicator(
                    progress = anime.totalWatchedEpisodes.toFloat() / anime.totalEpisodes.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}