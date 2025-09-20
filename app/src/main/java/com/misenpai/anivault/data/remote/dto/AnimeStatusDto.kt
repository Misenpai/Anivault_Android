package com.misenpai.anivault.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.misenpai.anivault.domain.model.WatchStatus

data class AnimeStatusListResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: AnimeStatusResult?
)

data class AnimeStatusResult(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("payload")
    val payload: List<AnimeStatusDto>?
)

data class AnimeStatusResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: SingleAnimeStatusResult?
)

data class SingleAnimeStatusResult(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("payload")
    val payload: AnimeStatusDto?
)

data class AnimeStatusDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("anime_name")
    val animeName: String,
    @SerializedName("total_watched_episodes")
    val totalWatchedEpisodes: Int,
    @SerializedName("total_episodes")
    val totalEpisodes: Int,
    @SerializedName("status")
    val status: String
)

data class AddAnimeStatusRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("anime_name")
    val animeName: String,
    @SerializedName("total_watched_episodes")
    val totalWatchedEpisodes: Int,
    @SerializedName("total_episodes")
    val totalEpisodes: Int,
    @SerializedName("status")
    val status: String
)

data class UpdateAnimeStatusRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("total_watched_episodes")
    val totalWatchedEpisodes: Int,
    @SerializedName("status")
    val status: String
)