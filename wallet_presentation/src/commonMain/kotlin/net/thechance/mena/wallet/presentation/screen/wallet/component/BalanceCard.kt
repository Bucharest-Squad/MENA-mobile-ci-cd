package net.thechance.mena.wallet.presentation.screen.wallet.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.current_balance
import mena.wallet_presentation.generated.resources.img_silver
import mena.wallet_presentation.generated.resources.silver_coin
import net.thechance.mena.designsystem.presentation.component.image.MenaImage
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.wallet.presentation.util.formatBalance
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BalanceCard(
    balance: Double,
    modifier: Modifier = Modifier,
) {
    var isCoinAnimationFinished by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(100)
        isCoinAnimationFinished = true
    }

    BoxWithConstraints(
        modifier = Modifier
            .background(
                color = Theme.colorScheme.background.surface,
                shape = RoundedCornerShape(Theme.radius.xl)
            ).border(
                width = 2.dp,
                color = Theme.colorScheme.stroke,
                shape = RoundedCornerShape(Theme.radius.xl)
            )
            .clip(RoundedCornerShape(Theme.radius.xl))
            .then(modifier)
    ) {
        val parentWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val curvedShape = remember(parentWidthPx) {
            TopNotchShape(
                offset = parentWidthPx/2,
                circleRadius = 22.dp,
                cornerRadius = 24.dp,
                circleGap = 5.dp
            )
        }

        AnimatedVisibility(
            visible = isCoinAnimationFinished,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            ) { it },
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.TopCenter)
        ) {
            MenaImage(
                painter = painterResource(Res.drawable.img_silver),
                contentDescription = stringResource(Res.string.silver_coin),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(200.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp)
                .shadow(elevation = 32.dp, shape = curvedShape)
                .clip(curvedShape)
                .background(color = Theme.colorScheme.background.surface, shape = curvedShape)
                .align(Alignment.BottomCenter),
        ) {
            MenaText(
                text = formatBalance(balance),
                style = Theme.typography.headline.medium,
                color = Theme.colorScheme.shadePrimary,
                modifier = Modifier.padding(top = 45.dp)
            )
            MenaText(
                text = stringResource(Res.string.current_balance),
                style = Theme.typography.label.extraSmall,
                color = Theme.colorScheme.shadeSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 19.dp)
            )
        }
    }
}

@Preview
@Composable
private fun BalanceCardPreview() {
    MenaTheme {
        BalanceCard(balance = 530320.55)
    }
}