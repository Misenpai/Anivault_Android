package com.misenpai.anivault.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.misenpai.anivault.domain.model.AnimeStatus
import com.misenpai.anivault.domain.model.WatchStatus

@Entity(tableName = "anime_status", primaryKeys = ["userId", "malId"])
data class AnimeStatusEntity(
    val userId: Int,
    val malId: Int,
    val animeName: String,
    val animeImage: String,
    val totalWatchedEpisodes: Int,
    val totalEpisodes: Int,
    val status: WatchStatus,
    val score: Float? = null,
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): AnimeStatus = AnimeStatus(
        userId = userId,
        malId = malId,
        animeName = animeName,
        totalWatchedEpisodes = totalWatchedEpisodes,
        totalEpisodes = totalEpisodes,
        status = status
    )
}