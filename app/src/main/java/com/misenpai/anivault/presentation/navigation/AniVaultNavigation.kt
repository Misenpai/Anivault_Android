package com.misenpai.anivault.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AniVaultNavigation(
    navController: NavHostController,
    startDestination: String
){
    NavHost(
        navController=navController,
        startDestination=startDestination
    ){
        composable (Screen.GetStarted.route){
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

        composable(
            route = Screen.AnimeDetails.route,
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AnimeDetailsScreen(
                animeId = animeId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToListClick = {
                    navController.navigate(Screen.AddAnimeStatus.createRoute(animeId))
                }
            )
        }

        composable(
            route = Screen.AddAnimeStatus.route,
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AddAnimeStatusScreen(
                animeId = animeId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }
}