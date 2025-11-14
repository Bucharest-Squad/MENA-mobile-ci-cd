package net.thechance.mena.core_chat.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_prayer
import mena.core_chat_presentation.generated.resources.ic_tempreature
import mena.core_chat_presentation.generated.resources.next_prayer
import mena.core_chat_presentation.generated.resources.prayer_weather_pattern_shape
import net.thechance.mena.core_chat.presentation.screen.home.HomeScreenState
import net.thechance.mena.designsystem.presentation.component.text.Text
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing._12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(28.dp).clip(RoundedCornerShape(Theme.radius.sm))
                        .background(Color.White.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_tempreature),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
                Text(
                    text = "${weatherUiState.currentTemperature}°C, ${weatherUiState.weatherCondition}",
                    style = Theme.typography.label.small,
                    color = Theme.colorScheme.primary.onPrimaryBody,
                    modifier = Modifier.padding(start = Theme.spacing._4)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${weatherUiState.maxTemperature}°C - ${weatherUiState.minTemperature}°C",
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.primary.onPrimary,
                    modifier = Modifier.padding(end = Theme.spacing._12)
                )
            }
        }
        if (prayerUiState != null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 52.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(28.dp).clip(RoundedCornerShape(Theme.radius.sm))
                        .background(Color.White.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_prayer),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
                Text(
                    text = "${stringResource(Res.string.next_prayer)} ${prayerUiState.nextPrayerName} in:",
                    style = Theme.typography.label.small,
                    color = Theme.colorScheme.primary.onPrimaryBody,
                    modifier = Modifier.padding(start = Theme.spacing._4)
                )

                Spacer(Modifier.weight(1f))
                Text(
                    text = prayerUiState.nextPrayerTime,
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.primary.onPrimary,
                    modifier = Modifier.padding(end = Theme.spacing._12)
                )
            }
        }
    }
}