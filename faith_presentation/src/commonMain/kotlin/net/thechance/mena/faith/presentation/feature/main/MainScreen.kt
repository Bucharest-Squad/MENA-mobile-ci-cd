package net.thechance.mena.faith.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.faith_title
import mena.faith_presentation.generated.resources.ic_location
import mena.faith_presentation.generated.resources.ic_sunrise
import mena.faith_presentation.generated.resources.location
import mena.faith_presentation.generated.resources.sunrise_time_label
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.base.ObserveAsEffect
import net.thechance.mena.faith.presentation.feature.main.componant.PrayerTimesCard
import net.thechance.mena.faith.presentation.feature.main.componant.SunriseTimeRow
import net.thechance.mena.faith.presentation.feature.main.componant.TilawahSection
import net.thechance.mena.faith.presentation.navigation.CalibrateDeviceRoute
import net.thechance.mena.faith.presentation.navigation.LocalNavController
import net.thechance.mena.faith.presentation.navigation.SurRoute
import net.thechance.mena.faith.presentation.navigation.SurahDetailsRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            is MainScreenEffect.NavigateToSurah -> {
                navController.navigate(
                    SurahDetailsRoute(
                        surahId = effect.surahId,
                        surahName = effect.surahName
                    )
                )
            }

            MainScreenEffect.NavigateToQuran -> navController.navigate(SurRoute)
            MainScreenEffect.NavigateToQiblah -> navController.navigate(CalibrateDeviceRoute)
            MainScreenEffect.NavigateToMosques -> {}
        }
    }

    Content(
        uiState = uiState,
        listener = viewModel
    )
}

@Composable
private fun Content(
    uiState: MainScreenState,
    listener: MainInteractionListener
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(Res.string.faith_title),
                trailingContent = {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Theme.colorScheme.background.surfaceLow,
                                shape = RoundedCornerShape(Theme.radius.full)
                            )
                            .padding(horizontal = Theme.spacing._8, vertical = Theme.spacing._4),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_location),
                            contentDescription = stringResource(Res.string.location),
                            tint = Theme.colorScheme.shadePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Baghdad, Iraq",
                            color = Theme.colorScheme.shadePrimary,
                            style = Theme.typography.label.small
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Theme.spacing._8, top = Theme.spacing._16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item { PrayerTimesCard(prayerTimesUiState = uiState.prayerTimesUiState) }

            item {

                Text(
                    text = uiState.hijriDate,
                    color = Theme.colorScheme.shadeSecondary,
                    style = Theme.typography.label.extraSmall
                )

            }

            item {
                SunriseTimeRow(
                    icon = painterResource(Res.drawable.ic_sunrise),
                    title = stringResource(Res.string.sunrise_time_label),
                    time = uiState.sunriseTime
                )
            }

            item {
                TilawahSection(
                    tilawahUiState = uiState.tilawahUiState,
                    onContinueTilawahClick = {
                        uiState.tilawahUiState?.let { tilawah ->
                            listener.onContinueTilawahClick(tilawah.surahId, tilawah.surahName)
                        }
                    },
                    onQuranClick = listener::onQuranClick,
                    onQiblahClick = listener::onQiblahClick,
                    onMosquesClick = listener::onMosquesClick
                )
            }
        }
    }
}

