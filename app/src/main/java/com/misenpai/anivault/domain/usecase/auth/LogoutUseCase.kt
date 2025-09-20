package com.misenpai.anivault.domain.usecase.auth

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return authRepository.logout()
    }
}