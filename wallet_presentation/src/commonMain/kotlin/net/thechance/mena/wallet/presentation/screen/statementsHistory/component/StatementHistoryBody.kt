package net.thechance.mena.wallet.presentation.screen.statementsHistory.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.thechance.mena.wallet.presentation.screen.statementsHistory.StatementsHistoryInteractionListener
import net.thechance.mena.wallet.presentation.screen.statementsHistory.StatementsHistoryScreenState

@Composable
fun StatementHistoryBody(
    state: StatementsHistoryScreenState,
    listener: StatementsHistoryInteractionListener
) {
    when {
        state.statements.isEmpty() -> EmptyStatementsHistory(modifier = Modifier.fillMaxSize())
        else -> StatementsListContent(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            listener = listener,
            state = state
        )
    }
}