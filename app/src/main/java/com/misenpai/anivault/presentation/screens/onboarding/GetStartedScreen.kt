package com.misenpai.anivault.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misenpai.anivault.R
import com.misenpai.anivault.presentation.theme.VioletPrimary

@Composable
fun GetStartedScreen(
    onGetStartedClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Fan Art Image
            Image(
                painter = painterResource(id = R.drawable.fan_art_removebg),
                contentDescription = "Fan Art",
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome Text
            Row {
                Text(
                    text = "Welcome to ",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "AniVault",
                    color = VioletPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description Text
            Text(
                text = "Begin your anime journey with AnimeVault, your ultimate tracking companion!",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Get Started Button
            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(17.dp)
            ) {
                Text(
                    text = "GET STARTED",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )
            }
        }
    }
}