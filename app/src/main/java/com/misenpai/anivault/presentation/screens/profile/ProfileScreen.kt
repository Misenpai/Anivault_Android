package com.misenpai.anivault.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val logoutState by viewModel.logoutState.collectAsStateWithLifecycle()

    LaunchedEffect(logoutState) {
        if (logoutState is LogoutState.Success) {
            onLogout()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when (profileState) {
            is ProfileState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is ProfileState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) {
                        if ((profileState as ProfileState.Success).user.avatar != null) {
                            AsyncImage(
                                model = (profileState as ProfileState.Success).user.avatar,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Default Avatar",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // User Name
                    Text(
                        text = (profileState as ProfileState.Success).user.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // User Email
                    Text(
                        text = (profileState as ProfileState.Success).user.email,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Profile Options
                    ProfileMenuItem(
                        icon = Icons.Default.Edit,
                        title = "Edit Profile",
                        onClick = { /* Navigate to edit profile */ }
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        title = "Settings",
                        onClick = { /* Navigate to settings */ }
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        title = "About",
                        onClick = { /* Show about dialog */ }
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Share,
                        title = "Share App",
                        onClick = { /* Share app */ }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Logout Button
                    Button(
                        onClick = { viewModel.logout() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        enabled = logoutState !is LogoutState.Loading
                    ) {
                        if (logoutState is LogoutState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = "Logout",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // App Version
                    Text(
                        text = "AniVault v0.01",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            is ProfileState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (profileState as ProfileState.Error).message,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Retry */ }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}