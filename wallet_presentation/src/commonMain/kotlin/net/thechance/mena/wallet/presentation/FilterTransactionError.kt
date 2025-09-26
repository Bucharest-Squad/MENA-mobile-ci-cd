import androidx.compose.runtime.Composable
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.filter_transaction_error_description
import mena.wallet_presentation.generated.resources.filter_transaction_error_title
import mena.wallet_presentation.generated.resources.ic_filter
import net.thechance.mena.wallet.presentation.component.StatePlaceholder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterTransactionError() {
    StatePlaceholder(
        image = painterResource(Res.drawable.ic_filter),
        title = stringResource(Res.string.filter_transaction_error_title),
        description = stringResource(Res.string.filter_transaction_error_description),
    )
}

@Preview
@Composable
private fun FilterTransactionErrorPreview() {
    FilterTransactionError()
}