package com.misenpai.anivault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.misenpai.anivault.presentation.navigation.AniVaultNavigation
import com.misenpai.anivault.presentation.screens.splash.SplashViewModel
import com.misenpai.anivault.ui.theme.AnivaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val splashViewModel: SplashViewModel = hiltViewModel()
            val isLoading by splashViewModel.isLoading.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition { isLoading }
            AnivaultTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AniVaultApp(splashViewModel = splashViewModel)
                }
            }
        }
    }
}

@Composable
fun AniVaultApp(splashViewModel: SplashViewModel) {
    val navController = rememberNavController()
    val startDestination by splashViewModel.startDestination.collectAsStateWithLifecycle()

    AniVaultNavigation(
        navController = navController,
        startDestination = startDestination
    )
}