package net.thechance.mena.core_chat.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_prayer
import mena.core_chat_presentation.generated.resources.ic_tempreature
import mena.core_chat_presentation.generated.resources.`in`
import mena.core_chat_presentation.generated.resources.next_prayer
import mena.core_chat_presentation.generated.resources.prayer_weather_pattern_shape
import net.thechance.mena.core_chat.presentation.screen.home.HomeScreenState
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeatherAndNextPrayerCard(
    prayerUiState: HomeScreenState.PrayerUiState? = null,
    weatherUiState: HomeScreenState.WeatherUiState? = null,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(92.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.colorScheme.primary.primary)
            .padding(start = Theme.spacing._12)
    ) {
        Image(
            painter = painterResource(Res.drawable.prayer_weather_pattern_shape),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
        )
        if (weatherUiState != null) {
            InfoCard(
                leadingIcon = painterResource(Res.drawable.ic_tempreature),
                leadingText = "${weatherUiState.currentTemperature}°C, ${weatherUiState.weatherCondition}",
                trailingText = "${weatherUiState.maxTemperature}°C - ${weatherUiState.minTemperature}°C",
                modifier = Modifier.padding(top = Theme.spacing._12)
            )
        }
        if (prayerUiState != null) {
            InfoCard(
                leadingIcon = painterResource(Res.drawable.ic_prayer),
                leadingText = "${stringResource(Res.string.next_prayer)} ${prayerUiState.nextPrayerName} ${stringResource(Res.string.`in`)}:",
                trailingText = prayerUiState.nextPrayerTime,
                modifier = Modifier.padding(top = 52.dp)
            )
        }
    }
}