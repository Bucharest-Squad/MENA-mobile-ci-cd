package net.thechance.mena.core_chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import mena.core_chat_presentation.generated.resources.ic_warning
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import mena.core_chat_presentation.generated.resources.Res
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnimatedSnackBarHost(
    isVisible: Boolean,
    data: SnackBarData,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Theme.spacing._12)
    ) {
        LaunchedEffect(data) {
            delay(data.duration)
            onDismiss()
        }
        SnackBar(
            modifier = modifier.clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = null
            ),
            title = stringResource(data.title),
            message = stringResource(data.message),
            leadingIcon = painterResource(Res.drawable.ic_warning),
        )
    }
}

data class SnackBarData(
    val title: StringResource,
    val message: StringResource,
    val duration: Long = 2500
)