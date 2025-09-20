package com.misenpai.anivault.domain.repository



import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.data.local.dao.UserDao
import com.misenpai.anivault.data.local.entities.toEntity
import com.misenpai.anivault.data.local.preferences.UserPreferences
import com.misenpai.anivault.data.remote.api.AniVaultApi
import com.misenpai.anivault.data.remote.dto.UpdateProfileRequest
import com.misenpai.anivault.domain.model.User
import com.misenpai.anivault.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: AniVaultApi,
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> = flow {
        val userId = userPreferences.getUserId()
        if (userId != -1) {
            userDao.getUserById(userId).map { it?.toDomainModel() }.collect { emit(it) }
        } else {
            emit(null)
        }
    }

    override suspend fun updateUserProfile(user: User): Resource<User> {
        return try {
            val request = UpdateProfileRequest(
                name = user.name,
                phone = user.phone,
                avatar = user.avatar
            )

            val response = api.updateUserProfile(user.id, request, "Bearer ${user.token}")

            response.result?.payload?.let { payload ->
                val updatedUser = payload.toUser(user.token)
                userDao.updateUser(updatedUser.toEntity())
                Resource.Success(updatedUser)
            } ?: Resource.Error("Failed to update profile")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun deleteUserAccount(userId: Int): Resource<Unit> {
        return try {
            userDao.deleteAllUsers()
            userPreferences.clearAll()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete account")
        }
    }
}