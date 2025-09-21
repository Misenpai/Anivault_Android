package com.misenpai.anivault.presentation.screens.home.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.misenpai.anivault.core.constants.Constants

@Composable
fun ArchiveTab() {
    val years = (2015..2025).reversed().toList()
    val seasons = listOf(
        Constants.AnimeSeason.WINTER,
        Constants.AnimeSeason.SPRING,
        Constants.AnimeSeason.SUMMER,
        Constants.AnimeSeason.FALL
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(years) { year ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = year.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        seasons.forEach { season ->
                            OutlinedButton(
                                onClick = { /* Navigate to season anime */ },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(season.capitalize())
                            }
                        }
                    }
                }
            }
        }
    }
}