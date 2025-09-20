package com.misenpai.anivault.presentation.navigation

sealed class Screen(val route: String){
    object Splash: Screen("splash")
    object GetStarted: Screen("get_started")
    object Login: Screen("login")
    object Signup: Screen("signup")
    object Main: Screen("main")
    object AnimeDetails: Screen("anime_details/{animeId}"){
        fun createRoute(animeId: Int) = "anime_details/$animeId"
    }
    object AddAnimeStatus: Screen("add_anime_status/{animeId"){
        fun createRoute(animeId: Int) = "add_anime_status/$animeId"
    }
}

sealed class BottomNavScreen(val route: String, val title: String){
    object Home: BottomNavScreen("home","Home")
    object Search: BottomNavScreen("search","Search")
    object Library: BottomNavScreen("library", "Library")
    object Profile: BottomNavScreen("profile","Profile")
}