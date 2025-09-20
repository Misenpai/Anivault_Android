package com.misenpai.anivault.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misenpai.anivault.data.local.preferences.UserPreferences
import com.misenpai.anivault.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _startDestination = MutableStateFlow(Screen.GetStarted.route)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            delay(1000) // Minimum splash duration

            userPreferences.isLoggedIn.collect { isLoggedIn ->
                _startDestination.value = if (isLoggedIn) {
                    Screen.Main.route
                } else {
                    Screen.GetStarted.route
                }
                _isLoading.value = false
            }
        }
    }
}