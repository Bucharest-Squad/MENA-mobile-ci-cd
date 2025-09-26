import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.filter_transaction_error_description
import mena.wallet_presentation.generated.resources.filter_transaction_error_title
import mena.wallet_presentation.generated.resources.ic_filter
import net.thechance.mena.wallet.presentation.component.StateMessage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterTransactionError() {
    StateMessage(
        image = painterResource(Res.drawable.ic_filter),
        title = stringResource(Res.string.filter_transaction_error_title),
        description = stringResource(Res.string.filter_transaction_error_description),
        imageModifier = Modifier.size(128.dp)
    )
}

@Preview
@Composable
private fun FilterTransactionErrorPreview() {
    FilterTransactionError()
}