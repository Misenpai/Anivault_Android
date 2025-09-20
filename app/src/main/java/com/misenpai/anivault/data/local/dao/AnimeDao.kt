package com.misenpai.anivault.data.local.dao

import androidx.room.*
import com.misenpai.anivault.data.local.entities.AnimeStatusEntity
import com.misenpai.anivault.domain.model.WatchStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeStatus(animeStatus: AnimeStatusEntity)

    @Query("SELECT * FROM anime_status WHERE userId = :userId")
    fun getUserAnimeList(userId: Int): Flow<List<AnimeStatusEntity>>

    @Query("SELECT * FROM anime_status WHERE userId = :userId AND status = :status")
    fun getUserAnimeByStatus(userId: Int, status: WatchStatus): Flow<List<AnimeStatusEntity>>

    @Query("SELECT * FROM anime_status WHERE userId = :userId AND malId = :malId")
    suspend fun getAnimeStatus(userId: Int, malId: Int): AnimeStatusEntity?

    @Update
    suspend fun updateAnimeStatus(animeStatus: AnimeStatusEntity)

    @Delete
    suspend fun deleteAnimeStatus(animeStatus: AnimeStatusEntity)

    @Query("DELETE FROM anime_status WHERE userId = :userId")
    suspend fun deleteUserAnimeList(userId: Int)
}