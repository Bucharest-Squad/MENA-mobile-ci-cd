package net.thechance.mena.admin_panel.presentation.screen.dukan_requests

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)

interface DukanRequestsInteractionListener {
    fun onSortClicked(type: DukanRequestsScreenState.SortType)
    fun onViewDetailsClicked(dukanId: Uuid)
    fun onRetryClicked()
    fun onPageChanged(page: Int)
    fun onApproveClicked(dukanId: Uuid)
    fun onRejectDialogClicked(dukanId: Uuid)
    fun onRejectDialogCanceled()
    fun onRejectConfirmed()
}