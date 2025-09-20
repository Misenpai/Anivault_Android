package com.misenpai.anivault.data.remote.api

import com.misenpai.anivault.data.remote.dto.*
import retrofit2.http.*

interface AniVaultApi {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("users/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    @GET("users/{userId}")
    suspend fun getUserProfile(
        @Path("userId") userId: Int,
        @Header("Authorization") token: String
    ): UserResponse

    @PUT("users/{userId}")
    suspend fun updateUserProfile(
        @Path("userId") userId: Int,
        @Body request: UpdateProfileRequest,
        @Header("Authorization") token: String
    ): UserResponse

    @GET("anime-status/user/{userId}")
    suspend fun getUserAnimeList(
        @Path("userId") userId: Int,
        @Header("Authorization") token: String
    ): AnimeStatusListResponse

    @POST("anime-status")
    suspend fun addAnimeStatus(
        @Body request: AddAnimeStatusRequest,
        @Header("Authorization") token: String
    ): AnimeStatusResponse

    @PUT("anime-status")
    suspend fun updateAnimeStatus(
        @Body request: UpdateAnimeStatusRequest,
        @Header("Authorization") token: String
    ): AnimeStatusResponse

    @DELETE("anime-status/{userId}/{malId}")
    suspend fun deleteAnimeStatus(
        @Path("userId") userId: Int,
        @Path("malId") malId: Int,
        @Header("Authorization") token: String
    ): BaseResponse
}