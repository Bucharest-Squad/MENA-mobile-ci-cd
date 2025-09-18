package net.thechance.mena.identity.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.app_name
import mena.identity_presentation.generated.resources.madimi_one_regular
import mena.identity_presentation.generated.resources.mena_logo
import net.thechance.mena.designsystem.presentation.component.image.MenaImage
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MenaLogo(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenaImage(
                painter = painterResource(Res.drawable.mena_logo),
                modifier = Modifier.size(88.dp)
            )
            MenaText(
                text = stringResource(Res.string.app_name),
                textAlign = TextAlign.Center,
                style = TextStyle.Default.copy(
                    fontSize = 28.sp,
                    fontFamily = FontFamily(
                        Font(
                            resource = Res.font.madimi_one_regular,
                            FontWeight.Normal
                        )
                    ),
                    color = Theme.colorScheme.shadePrimary
                )
            )
        }
    }
}