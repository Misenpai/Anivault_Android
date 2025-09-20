package com.misenpai.anivault.domain.repository

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUserProfile(user: User): Resource<User>
    suspend fun deleteUserAccount(userId: Int): Resource<Unit>
}