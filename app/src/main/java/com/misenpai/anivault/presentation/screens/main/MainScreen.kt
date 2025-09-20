package com.misenpai.anivault.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import com.misenpai.anivault.R
import com.misenpai.anivault.presentation.navigation.BottomNavScreen


@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Library,
        BottomNavScreen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = getIconForScreen(screen)),
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomNavScreen.Home.route
            ) {
                composable(BottomNavScreen.Home.route) {
                    HomeScreen()
                }
                composable(BottomNavScreen.Search.route) {
                    SearchScreen()
                }
                composable(BottomNavScreen.Library.route) {
                    LibraryScreen()
                }
                composable(BottomNavScreen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}

private fun getIconForScreen(screen: BottomNavScreen): Int {
    return when (screen) {
        BottomNavScreen.Home -> R.drawable.home
        BottomNavScreen.Search -> R.drawable.search
        BottomNavScreen.Library -> R.drawable.baseline_library_add_24
        BottomNavScreen.Profile -> R.drawable.baseline_person_24
    }
}
