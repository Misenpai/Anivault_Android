package com.misenpai.anivault.domain.repository

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun signup(name: String, email: String, password: String): Resource<User>
    suspend fun logout(): Resource<Unit>
    fun getCurrentUser(): Flow<User?>
    suspend fun clearUserData()
}