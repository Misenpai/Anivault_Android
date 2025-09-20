package com.misenpai.anivault.domain.usecase.auth

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.User
import com.misenpai.anivault.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        return authRepository.login(email, password)
    }
}