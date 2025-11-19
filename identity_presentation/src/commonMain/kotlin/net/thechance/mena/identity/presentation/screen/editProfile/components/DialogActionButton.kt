package net.thechance.mena.identity.presentation.screen.editProfile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.delete_account
import mena.identity_presentation.generated.resources.delete_account_description
import mena.identity_presentation.generated.resources.delete_account_title
import net.thechance.mena.designsystem.presentation.component.button.TextButton
import net.thechance.mena.designsystem.presentation.component.dialog.Dialog
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DialogActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Theme.colorScheme.error
) {
    TextButton(
        modifier = modifier
            .padding(vertical = Theme.spacing._24, horizontal = Theme.spacing._12),
        text = text,
        onClick = onClick,
        contentColor = contentColor
    )
}