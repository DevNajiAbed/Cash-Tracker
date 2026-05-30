package com.naji.cashtracker.feature.splash.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.naji.cashtracker.R
import com.naji.cashtracker.core.presentation.ObserveAsEvents
import com.naji.cashtracker.ui.theme.CashTrackerTheme
import com.naji.cashtracker.ui.theme.DarkPrimary
import com.naji.cashtracker.ui.theme.InterFont
import com.naji.cashtracker.ui.theme.LightPrimary
import org.koin.androidx.compose.koinViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*

@Composable
fun SplashRoot(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            SplashEvent.NavigateToOnboarding -> onNavigateToOnboarding()
            SplashEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    SplashScreen()
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightPrimary, DarkPrimary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    color = Color.White,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp
                )
            }

            Text(
                text = "Cash Track",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Text(
                text = stringResource(R.string.splash_tagline),
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    CashTrackerTheme {
        SplashScreen()
    }
}

val splashPresentationModule: Module = module {
    viewModelOf(::SplashViewModel)
}
