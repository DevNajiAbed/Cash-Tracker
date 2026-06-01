package com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.cashtracker.R
import kotlinx.coroutines.launch
import com.naji.cashtracker.core.presentation.ObserveAsEvents
import com.naji.cashtracker.core.presentation.components.CashTrackerPrimaryButton
import com.naji.cashtracker.ui.theme.CashTrackerTheme
import com.naji.cashtracker.ui.theme.LightPrimaryContainer
import com.naji.cashtracker.ui.theme.LightSecondaryContainer
import com.naji.cashtracker.ui.theme.LightTertiaryContainer
import org.koin.androidx.compose.koinViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*

private val titleResIds = listOf(
    R.string.onboarding_title_1,
    R.string.onboarding_title_2,
    R.string.onboarding_title_3
)

private val descResIds = listOf(
    R.string.onboarding_desc_1,
    R.string.onboarding_desc_2,
    R.string.onboarding_desc_3
)

@Composable
fun OnboardingRoot(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            OnboardingEvent.NavigateToRegister -> onComplete()
        }
    }

    OnboardingScreen(
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun OnboardingScreen(
    onAction: (OnboardingAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                when (page) {
                    0 -> Step1Illustration()
                    1 -> Step2Illustration()
                    2 -> Step3Illustration()
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(titleResIds[page]),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(descResIds[page]),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

            }
        }

        PageIndicator(
            currentPage = pagerState.currentPage,
            totalPages = pagerState.pageCount
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.pageCount - 1)
                        onAction(OnboardingAction.OnComplete)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.onboarding_skip),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(modifier = Modifier.width(140.dp)) {
                CashTrackerPrimaryButton(
                    text = if (pagerState.currentPage == pagerState.pageCount - 1)
                        stringResource(R.string.onboarding_get_started)
                    else
                        stringResource(R.string.onboarding_next),
                    onClick = {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onAction(OnboardingAction.OnComplete)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun PageIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val animatedWidth by animateDpAsState(
                targetValue = if (index == currentPage) 24.dp else 8.dp,
                animationSpec = tween(300)
            )
            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                animationSpec = tween(300)
            )
            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .height(8.dp)
                    .clip(
                        if (index == currentPage) RoundedCornerShape(4.dp)
                        else CircleShape
                    )
                    .background(animatedColor)
            )
        }
    }
}

@Composable
private fun Step1Illustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(150.dp, 110.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
                .offset(x = 5.dp, y = 5.dp)
                .clip(CircleShape)
                .background(LightPrimaryContainer)
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-8).dp)
                .clip(CircleShape)
                .background(LightSecondaryContainer)
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-24).dp, y = 28.dp)
                .clip(CircleShape)
                .background(LightTertiaryContainer)
        )

        Box(
            modifier = Modifier
                .width(70.dp)
                .height(3.dp)
                .align(Alignment.TopStart)
                .offset(x = 32.dp, y = 64.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.outline)
        )

        Box(
            modifier = Modifier
                .width(44.dp)
                .height(3.dp)
                .align(Alignment.TopStart)
                .offset(x = 32.dp, y = 74.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

@Composable
private fun Step2Illustration(modifier: Modifier = Modifier) {
    val barColors = listOf(
        LightPrimaryContainer,
        LightSecondaryContainer,
        LightTertiaryContainer,
        LightPrimaryContainer
    )
    val barHeights = listOf(40, 60, 30, 48)

    Box(
        modifier = modifier.size(150.dp, 110.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            barHeights.forEachIndexed { index, height ->
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(height.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 6.dp,
                                topEnd = 6.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(barColors[index])
                )
            }
        }
    }
}

@Composable
private fun Step3Illustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(150.dp, 110.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .offset(y = (-10).dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .border(3.dp, LightPrimaryContainer, CircleShape)
            )

            Box(
                modifier = Modifier
                    .size(54.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                        .border(3.dp, LightSecondaryContainer, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary)
                )
            }
        }
    }
}

@Preview(/*uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL*/)
@Composable
private fun PreviewOnboardingScreen() {
    CashTrackerTheme {
        OnboardingScreen(
            onAction = {}
        )
    }
}

val onboardingPresentationModule: Module = module {
    viewModelOf(::OnboardingViewModel)
}
