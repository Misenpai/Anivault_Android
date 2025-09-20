package com.misenpai.anivault.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun login() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = loginUseCase(email, password)) {
                is Resource.Success<*> -> {
                    _uiState.value = LoginUiState.Success
                }
                is Resource.Error<*> -> {
                    _uiState.value = LoginUiState.Error(
                        result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading<*> -> {
                    _uiState.value = LoginUiState.Loading
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        when {
            email.isBlank() -> {
                _uiState.value = LoginUiState.Error("Please enter your email")
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = LoginUiState.Error("Please enter a valid email")
                return false
            }
            password.isBlank() -> {
                _uiState.value = LoginUiState.Error("Please enter your password")
                return false
            }
            password.length < 6 -> {
                _uiState.value = LoginUiState.Error("Password must be at least 6 characters")
                return false
            }
        }
        return true
    }
}



sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}