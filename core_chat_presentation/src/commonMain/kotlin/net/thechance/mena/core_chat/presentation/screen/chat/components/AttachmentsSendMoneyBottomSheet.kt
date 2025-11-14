package net.thechance.mena.core_chat.presentation.screen.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.amount
import mena.core_chat_presentation.generated.resources.ic_coin
import mena.core_chat_presentation.generated.resources.ic_wallet_add
import mena.core_chat_presentation.generated.resources.send
import mena.core_chat_presentation.generated.resources.send_a_money
import net.thechance.mena.core_chat.presentation.screen.chat.AttachmentsSendMoneyInteractionListener
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AttachmentsSendMoneyBottomSheet(
    value: String,
    isEnabled: Boolean,
    onValueChange: (String) -> Unit,
    attachmentsInteractionListener: AttachmentsSendMoneyInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RectangleShape, shadow = Shadow(
                    radius = Theme.spacing._12,
                    spread = 0.dp,
                    color = Color.Black.copy(alpha = .06f),
                    offset = DpOffset(0.dp, (-2).dp)
                )
            )
            .background(
                color = Theme.colorScheme.background.surface,
                shape = RoundedCornerShape(topStart = Theme.radius.xl, topEnd = Theme.radius.xl)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(
                    width = 39.dp,
                    height = 2.dp
                )
                .background(
                    color = Theme.colorScheme.shadeTertiary,
                    shape = RoundedCornerShape(Theme.radius.full)
                ),
        )
        AttachmentSendMoneyBottomSheetContent(
            value = value,
            isEnabled = isEnabled,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            attachmentsInteractionListener = attachmentsInteractionListener
        )

    }
}

@Composable
private fun AttachmentSendMoneyBottomSheetContent(
    value: String,
    isEnabled: Boolean,
    onValueChange: (String) -> Unit,
    attachmentsInteractionListener: AttachmentsSendMoneyInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = Theme.spacing._16).fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(vertical = Theme.spacing._16),
            text = stringResource(Res.string.send_a_money),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary
        )
        Text(
            modifier = Modifier.padding(bottom = Theme.spacing._4),
            text = stringResource(Res.string.amount),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary
        )
        TextField(
            value = value,
            hint = "",
            onValueChanged = { input ->
                val numericRegex = Regex("^\\d*([.,])?\\d*$")
                if (input.isEmpty() || numericRegex.matches(input)) {
                    onValueChange(input)
                }
            },
            modifier = Modifier.padding(bottom = Theme.spacing._16).fillMaxWidth(),
            leadingIcon = painterResource(Res.drawable.ic_wallet_add),
            trailingIcon = painterResource(Res.drawable.ic_coin),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        PrimaryButton(
            text = stringResource(Res.string.send),
            onClick = attachmentsInteractionListener::onSendClicked,
            modifier = Modifier.padding(bottom = Theme.spacing._24).fillMaxWidth(),
            isEnabled = isEnabled
        )

    }
}

@Composable
@Preview()
private fun AttachmentsSendMoneyBottomSheetPreview() {
    MenaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.surface)
        ) {
            AttachmentsSendMoneyBottomSheet(
                value = "",
                isEnabled = false,
                onValueChange = {},
                attachmentsInteractionListener = object : AttachmentsSendMoneyInteractionListener {
                    override fun onValueChanged(value: String) {}

                    override fun onSendClicked() {}

                },
            )
        }
    }
}
