package net.thechance.mena.wallet.presentation.screen.export.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import net.thechance.mena.wallet.presentation.component.filter.FilterContent
import net.thechance.mena.wallet.presentation.screen.export.ExportTransactionsListener
import net.thechance.mena.wallet.presentation.screen.export.ExportTransactionsState

@Composable
fun FilterSection(
    state: ExportTransactionsState,
    interactionListener: ExportTransactionsListener
) {
    Column {
        horizontalDivider()
        FilterContent(
            selectedTypes = state.selectedTransactionsTypes,
            selectedStatus = state.selectedTransactionsStatus,
            fromDate = state.startDate ?: "",
            toDate = state.endDate ?: "",
            onTypeSelected = interactionListener::onTypeSelected,
            onStatusSelected = interactionListener::onStatusSelected,
            onFromClick = interactionListener::onFromDateClicked,
            onToClick = interactionListener::onToDateClicked
        )
    }
}
