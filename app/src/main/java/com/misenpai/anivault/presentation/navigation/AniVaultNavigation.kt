package com.misenpai.anivault.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.misenpai.anivault.presentation.screens.onboarding.GetStartedScreen
import com.misenpai.anivault.presentation.screens.auth.LoginScreen
import com.misenpai.anivault.presentation.screens.auth.SignupScreen
import com.misenpai.anivault.presentation.screens.main.MainScreen

@Composable
fun AniVaultNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.GetStarted.route) {
            GetStartedScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}