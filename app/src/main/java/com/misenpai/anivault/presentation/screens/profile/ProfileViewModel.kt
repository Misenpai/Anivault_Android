package com.misenpai.anivault.presentation.screens.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.User
import com.misenpai.anivault.domain.usecase.auth.LogoutUseCase
import com.misenpai.anivault.domain.usecase.user.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                if (user != null) {
                    _profileState.value = ProfileState.Success(user)
                } else {
                    _profileState.value = ProfileState.Error("User not found")
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = LogoutState.Loading

            when (logoutUseCase()) {
                is Resource.Success -> {
                    _logoutState.value = LogoutState.Success
                }
                is Resource.Error -> {
                    _logoutState.value = LogoutState.Error
                }
                is Resource.Loading -> {
                    _logoutState.value = LogoutState.Loading
                }
            }
        }
    }
}

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    object Error : LogoutState()
}