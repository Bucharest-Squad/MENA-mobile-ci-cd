package net.thechance.mena.wallet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.img_filter_error
import net.thechance.mena.designsystem.presentation.component.image.Image
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource

@Composable
fun FilterTransactionError() {

    val Icon = Res.drawable.img_filter_error
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(width = 144.dp, height = 120.dp),
            painter = painterResource(Icon),
            contentDescription = "error_image"
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "gehad error",
            color = Theme.colorScheme.shadePrimary,
            style = Theme.typography.title.small,
        )
        Text(
            text = "Change your filters or reset them to explore all transactions",
            color = Theme.colorScheme.shadeSecondary,
            style = Theme.typography.body.small,
            textAlign = TextAlign.Center
        )
    }
}