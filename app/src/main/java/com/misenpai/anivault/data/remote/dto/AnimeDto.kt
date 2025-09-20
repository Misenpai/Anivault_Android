package com.misenpai.anivault.data.remote.dto
import com.google.gson.annotations.SerializedName
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.model.AnimeDetails

data class SeasonAnimeResponse(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("data")
    val data: List<AnimeDto>
)

data class AnimeResponse(
    @SerializedName("data")
    val data: AnimeDto
)

data class Pagination(
    @SerializedName("last_visible_page")
    val lastPage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("current_page")
    val currentPage: Int
)

data class AnimeDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String?,
    @SerializedName("images")
    val images: ImagesDto,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("status")
    val status: String,
    @SerializedName("airing")
    val airing: Boolean,
    @SerializedName("aired")
    val aired: AiredDto?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("scored_by")
    val scoredBy: Int?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("producers")
    val producers: List<NamedResourceDto>?,
    @SerializedName("studios")
    val studios: List<NamedResourceDto>?,
    @SerializedName("genres")
    val genres: List<NamedResourceDto>?,
    @SerializedName("themes")
    val themes: List<NamedResourceDto>?
) {
    fun toAnime(): Anime = Anime(
        malId = malId,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        imageUrl = images.jpg.largeImageUrl ?: images.jpg.imageUrl,
        type = type,
        episodes = episodes,
        status = status,
        airing = airing,
        score = score,
        rank = rank,
        synopsis = synopsis,
        season = season,
        year = year,
        genres = genres?.map { it.name } ?: emptyList()
    )
}

data class ImagesDto(
    @SerializedName("jpg")
    val jpg: ImageUrlsDto,
    @SerializedName("webp")
    val webp: ImageUrlsDto
)

data class ImageUrlsDto(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class AiredDto(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("string")
    val string: String?
)

data class NamedResourceDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String
)