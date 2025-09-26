package net.thechance.mena.wallet.presentation.screen.export.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.downloading_started
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomToast(
    toastState: CustomToastState,
    modifier: Modifier = Modifier
) {
    val toastBackgroundColor = Color(0xB2121212)
    Box(
        modifier = modifier
            .background(
                color = toastBackgroundColor,
                shape = RoundedCornerShape(Theme.radius.md)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = toastState.messageRes?.let { stringResource(it) } ?: "",
            style = Theme.typography.body.small,
            color = Theme.colorScheme.primary.onPrimary
        )

    }

}

@Preview
@Composable
private fun CustomToastPreview() {
    MenaTheme {
        CustomToast(
            toastState = CustomToastState(
                messageRes = Res.string.downloading_started
            )
        )
    }

}