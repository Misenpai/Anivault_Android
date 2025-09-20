package com.misenpai.anivault.domain.model

data class Anime(
    val malId: Int,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val imageUrl: String,
    val type: String?,
    val episodes: Int?,
    val status: String?,
    val airing: Boolean,
    val score: Double?,
    val rank: Int?,
    val synopsis: String?,
    val season: String?,
    val year: Int?,
    val genres: List<String>
)


data class AnimeDetails(
    val malId: Int,
    val url: String,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val images: AnimeImages,
    val type: String,
    val source: String,
    val episodes: Int?,
    val status: String,
    val airing: Boolean,
    val aired: String,
    val duration: String,
    val rating: String,
    val score: Double?,
    val rank: Int?,
    val synopsis: String,
    val season: String?,
    val year: Int?,
    val producers: List<String>,
    val licensors: List<String>,
    val studios: List<String>,
    val genres: List<String>,
    val themes: List<String>,
    val demographics: List<String>,
    val openings: List<String>,
    val endings: List<String>
)

data class AnimeImages(
    val jpg: ImageUrls,
    val webp: ImageUrls
)

data class ImageUrls(
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
)

data class AnimeStatus(
    val userId: Int,
    val malId: Int,
    val animeName: String,
    val totalWatchedEpisodes: Int,
    val totalEpisodes: Int,
    val status: WatchStatus
)

enum class WatchStatus {
    WATCHING,
    COMPLETED,
    DROPPED,
    PLAN_TO_WATCH
}