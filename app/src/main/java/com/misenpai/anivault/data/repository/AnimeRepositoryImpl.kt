package com.misenpai.anivault.data.repository

import com.misenpai.anivault.core.constants.Constants
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.data.local.dao.AnimeDao
import com.misenpai.anivault.data.local.entities.AnimeStatusEntity
import com.misenpai.anivault.data.local.preferences.UserPreferences
import com.misenpai.anivault.data.remote.api.AniVaultApi
import com.misenpai.anivault.data.remote.api.JikanApi
import com.misenpai.anivault.data.remote.dto.AddAnimeStatusRequest
import com.misenpai.anivault.data.remote.dto.UpdateAnimeStatusRequest
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.model.AnimeDetails
import com.misenpai.anivault.domain.model.AnimeImages
import com.misenpai.anivault.domain.model.AnimeStatus
import com.misenpai.anivault.domain.model.ImageUrls
import com.misenpai.anivault.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val jikanApi: JikanApi,
    private val aniVaultApi: AniVaultApi,
    private val animeDao: AnimeDao,
    private val userPreferences: UserPreferences
) : AnimeRepository {

    override suspend fun getAnimeById(id: Int): Resource<AnimeDetails> {
        return try {
            val response = jikanApi.getAnimeById(id)
            val anime = response.data
            Resource.Success(
                AnimeDetails(
                    malId = anime.malId,
                    url = anime.url ?: "",
                    title = anime.title.take(Constants.MAX_TITLE_LENGTH),
                    titleEnglish = anime.titleEnglish?.take(Constants.MAX_TITLE_LENGTH),
                    titleJapanese = anime.titleJapanese?.take(Constants.MAX_TITLE_LENGTH),
                    images = AnimeImages(
                        jpg = ImageUrls(
                            imageUrl = anime.images.jpg.imageUrl,
                            smallImageUrl = anime.images.jpg.smallImageUrl ?: "",
                            largeImageUrl = anime.images.jpg.largeImageUrl
                                ?: anime.images.jpg.imageUrl
                        ),
                        webp = ImageUrls(
                            imageUrl = anime.images.webp.imageUrl,
                            smallImageUrl = anime.images.webp.smallImageUrl ?: "",
                            largeImageUrl = anime.images.webp.largeImageUrl
                                ?: anime.images.webp.imageUrl
                        )
                    ),
                    type = anime.type ?: "",
                    source = anime.source ?: "",
                    episodes = anime.episodes,
                    status = anime.status,
                    airing = anime.airing,
                    aired = anime.aired?.string ?: "",
                    duration = anime.duration ?: "",
                    rating = anime.rating ?: "",
                    score = anime.score,
                    rank = anime.rank,
                    synopsis = anime.synopsis ?: "",
                    season = anime.season,
                    year = anime.year,
                    producers = anime.producers?.map { it.name } ?: emptyList(),
                    licensors = emptyList(),
                    studios = anime.studios?.map { it.name } ?: emptyList(),
                    genres = anime.genres?.map { it.name } ?: emptyList(),
                    themes = anime.themes?.map { it.name } ?: emptyList(),
                    demographics = emptyList(),
                    openings = emptyList(),
                    endings = emptyList()
                )
            )
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun searchAnime(query: String, page: Int): Resource<List<Anime>> {
        return try {
            val response = jikanApi.searchAnime(query, page, Constants.PAGE_SIZE)
            Resource.Success(response.data.map { it.toAnime() })
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getCurrentSeasonAnime(page: Int): Resource<List<Anime>> {
        return try {
            val response = jikanApi.getCurrentSeasonAnime(page, Constants.PAGE_SIZE)
            Resource.Success(response.data.map { it.toAnime() })
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getUpcomingSeasonAnime(page: Int): Resource<List<Anime>> {
        return try {
            val response = jikanApi.getUpcomingSeasonAnime(page, Constants.PAGE_SIZE)
            Resource.Success(response.data.map { it.toAnime() })
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getSeasonAnime(year: Int, season: String, page: Int): Resource<List<Anime>> {
        return try {
            val response = jikanApi.getSeasonAnime(year, season, page)
            Resource.Success(response.data.map { it.toAnime() })
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getTopAnime(page: Int): Resource<List<Anime>> {
        return try {
            val response = jikanApi.getTopAnime(page, Constants.PAGE_SIZE)
            Resource.Success(response.data.map { it.toAnime() })
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getUserAnimeList(userId: Int): Flow<List<AnimeStatus>> {
        return animeDao.getUserAnimeList(userId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addAnimeToList(animeStatus: AnimeStatus): Resource<Unit> {
        return try {
            val token = userPreferences.getToken() ?: return Resource.Error("No auth token found")

            animeDao.insertAnimeStatus(
                AnimeStatusEntity(
                    userId = animeStatus.userId,
                    malId = animeStatus.malId,
                    animeName = animeStatus.animeName,
                    animeImage = "",
                    totalWatchedEpisodes = animeStatus.totalWatchedEpisodes,
                    totalEpisodes = animeStatus.totalEpisodes,
                    status = animeStatus.status
                )
            )

            val request = AddAnimeStatusRequest(
                userId = animeStatus.userId,
                malId = animeStatus.malId,
                animeName = animeStatus.animeName,
                totalWatchedEpisodes = animeStatus.totalWatchedEpisodes,
                totalEpisodes = animeStatus.totalEpisodes,
                status = animeStatus.status.name
            )
            aniVaultApi.addAnimeStatus(request, "Bearer $token")

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add anime to list")
        }
    }

    override suspend fun updateAnimeStatus(animeStatus: AnimeStatus): Resource<Unit> {
        return try {
            val token = userPreferences.getToken() ?: return Resource.Error("No auth token found")

            val existing = animeDao.getAnimeStatus(animeStatus.userId, animeStatus.malId)
            existing?.let { entity ->
                animeDao.updateAnimeStatus(
                    entity.copy(
                        totalWatchedEpisodes = animeStatus.totalWatchedEpisodes,
                        status = animeStatus.status,
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }

            val request = UpdateAnimeStatusRequest(
                userId = animeStatus.userId,
                malId = animeStatus.malId,
                totalWatchedEpisodes = animeStatus.totalWatchedEpisodes,
                status = animeStatus.status.name
            )
            aniVaultApi.updateAnimeStatus(request, "Bearer $token")

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update anime status")
        }
    }

    override suspend fun deleteAnimeFromList(userId: Int, malId: Int): Resource<Unit> {
        return try {
            val token = userPreferences.getToken() ?: return Resource.Error("No auth token found")

            val entity = animeDao.getAnimeStatus(userId, malId)
            entity?.let { animeDao.deleteAnimeStatus(it) }

            aniVaultApi.deleteAnimeStatus(userId, malId, "Bearer $token")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete anime from list")
        }
    }
}