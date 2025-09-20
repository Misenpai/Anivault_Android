package com.misenpai.anivault.domain.usecase.user

import com.misenpai.anivault.domain.model.User
import com.misenpai.anivault.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<User?> {
        return userRepository.getCurrentUser()
    }
}