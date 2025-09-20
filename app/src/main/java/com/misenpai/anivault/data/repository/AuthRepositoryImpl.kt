package com.misenpai.anivault.data.repository

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.data.local.dao.UserDao
import com.misenpai.anivault.data.local.entities.toEntity
import com.misenpai.anivault.data.local.preferences.UserPreferences
import com.misenpai.anivault.data.remote.api.AniVaultApi
import com.misenpai.anivault.data.remote.dto.LoginRequest
import com.misenpai.anivault.data.remote.dto.SignupRequest
import com.misenpai.anivault.domain.model.User
import com.misenpai.anivault.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl @Inject constructor(
    private val api: AniVaultApi,
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            val response = api.login(LoginRequest(email, password))

            response.result?.payload?.let { payload ->
                val user = payload.toUser(response.token)
                userDao.insertUser(user.toEntity())
                userPreferences.saveToken(response.token ?: "")
                userPreferences.saveUserId(user.id)
                Resource.Success(user)
            } ?: Resource.Error("Invalid response from server")
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val response = api.signup(SignupRequest(name, email, password))

            response.result?.payload?.let { payload ->
                val user = payload.toUser(response.token)
                userDao.insertUser(user.toEntity())
                userPreferences.saveToken(response.token ?: "")
                userPreferences.saveUserId(user.id)
                Resource.Success(user)
            } ?: Resource.Error("Invalid response from server")
        } catch (e: HttpException) {
            when (e.code()) {
                409 -> Resource.Error("User with this email already exists")
                else -> Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            userDao.deleteAllUsers()
            userPreferences.clearToken()
            userPreferences.clearUserId()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error logging out")
        }
    }

    override fun getCurrentUser(): Flow<User?> = flow {
        val userId = userPreferences.getUserId()
        if (userId != -1) {
            userDao.getUserById(userId).map { it?.toDomainModel() }.collect { emit(it) }
        } else {
            emit(null)
        }
    }

    override suspend fun clearUserData() {
        userDao.deleteAllUsers()
        userPreferences.clearToken()
        userPreferences.clearUserId()
    }
}