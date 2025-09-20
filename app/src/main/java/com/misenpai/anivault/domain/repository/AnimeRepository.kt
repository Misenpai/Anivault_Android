package com.misenpai.anivault.domain.repository
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.model.AnimeDetails
import com.misenpai.anivault.domain.model.AnimeStatus
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnimeById(id: Int): Resource<AnimeDetails>
    suspend fun searchAnime(query: String, page: Int = 1): Resource<List<Anime>>
    suspend fun getCurrentSeasonAnime(page: Int = 1): Resource<List<Anime>>
    suspend fun getUpcomingSeasonAnime(page: Int = 1): Resource<List<Anime>>
    suspend fun getSeasonAnime(year: Int, season: String, page: Int = 1): Resource<List<Anime>>
    suspend fun getTopAnime(page: Int = 1): Resource<List<Anime>>

    // User anime list operations
    suspend fun getUserAnimeList(userId: Int): Flow<List<AnimeStatus>>
    suspend fun addAnimeToList(animeStatus: AnimeStatus): Resource<Unit>
    suspend fun updateAnimeStatus(animeStatus: AnimeStatus): Resource<Unit>
    suspend fun deleteAnimeFromList(userId: Int, malId: Int): Resource<Unit>
}