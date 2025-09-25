import androidx.compose.runtime.Composable
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.img_filter_error
import net.thechance.mena.wallet.presentation.component.StateMessage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterTransactionError() {
    StateMessage(
        image = painterResource(Res.drawable.img_filter_error),
        title ="Filters give no matches",
        description ="Change your filters or reset them to explore all transactions",
    )
}
@Preview
@Composable
private fun FilterTransactionErrorPreview(){
    FilterTransactionError()
}