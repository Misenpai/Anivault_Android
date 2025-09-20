package com.misenpai.anivault.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.usecase.auth.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    fun updateName(value: String) {
        name = value
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword = value
    }

    fun signup() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading

            when (val result = signupUseCase(name, email, password)) {
                is Resource.Success -> {
                    _uiState.value = SignupUiState.Success
                }
                is Resource.Error -> {
                    _uiState.value = SignupUiState.Error(
                        result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = SignupUiState.Loading
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        when {
            name.isBlank() -> {
                _uiState.value = SignupUiState.Error("Please enter your name")
                return false
            }
            email.isBlank() -> {
                _uiState.value = SignupUiState.Error("Please enter your email")
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = SignupUiState.Error("Please enter a valid email")
                return false
            }
            password.isBlank() -> {
                _uiState.value = SignupUiState.Error("Please enter your password")
                return false
            }
            password.length < 6 -> {
                _uiState.value = SignupUiState.Error("Password must be at least 6 characters")
                return false
            }
            confirmPassword != password -> {
                _uiState.value = SignupUiState.Error("Passwords do not match")
                return false
            }
        }
        return true
    }
}

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    object Success : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}