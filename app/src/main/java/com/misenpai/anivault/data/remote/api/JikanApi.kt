package com.misenpai.anivault.data.remote.api

import com.misenpai.anivault.data.remote.dto.AnimeResponse
import com.misenpai.anivault.data.remote.dto.SeasonAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {
    @GET("anime/{id}/full")
    suspend fun getAnimeById(@Path("id") id: Int): AnimeResponse

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): SeasonAnimeResponse

    @GET("seasons/now")
    suspend fun getCurrentSeasonAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): SeasonAnimeResponse

    @GET("seasons/upcoming")
    suspend fun getUpcomingSeasonAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): SeasonAnimeResponse

    @GET("seasons/{year}/{season}")
    suspend fun getSeasonAnime(
        @Path("year") year: Int,
        @Path("season") season: String,
        @Query("page") page: Int = 1
    ): SeasonAnimeResponse

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): SeasonAnimeResponse
}