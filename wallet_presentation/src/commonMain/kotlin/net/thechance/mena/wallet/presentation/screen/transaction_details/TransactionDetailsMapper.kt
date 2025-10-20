@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.wallet.presentation.screen.transaction_details

import androidx.compose.ui.graphics.ImageBitmap
import io.github.suwasto.capturablecompose.CompressionFormat
import io.github.suwasto.capturablecompose.toByteArray
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.from
import mena.wallet_presentation.generated.resources.purchase
import mena.wallet_presentation.generated.resources.receiver
import mena.wallet_presentation.generated.resources.to
import mena.wallet_presentation.generated.resources.transfer
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.model.TransactionType
import net.thechance.mena.wallet.presentation.screen.transaction_details.TransactionDetailsScreenState.TransactionStatusUiState
import net.thechance.mena.wallet.presentation.screen.transaction_details.TransactionDetailsScreenState.TransactionTypeUiState
import net.thechance.mena.wallet.presentation.utils.formatLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

fun Transaction.toUi() = TransactionDetailsScreenState.TransactionDetailsUiState(
    id = this.transactionId(),
    amount = this.amount(),
    date = this.transactionDate(),
    userName = this.userName(),
    otherParty = this.otherParty(),
    transactionType = this.transactionType(),
    transactionStatus = this.transactionStatus(),
    userInfo = this.userInfo(),
    typeContent = this.typeContent(),
    otherPartyTitle = this.otherPartyTitle()
)

fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray =
    imageBitmap.toByteArray(CompressionFormat.PNG, quality = Constants.PNG_QUALITY)

private fun Transaction.transactionId(): String =
    Constants.ID_PREFIX + id.toString().substring(0, Constants.ID_LENGTH)

private fun Transaction.amount(): String =
    amount.toString().replace(oldValue = ".", newValue = ",")

private fun Transaction.transactionDate(): String =
    formatLocalDateTime(date = createdAt, outputFormat = "dd MMM yyyy, h:mm a")

private fun Transaction.userName(): String = when (type) {
    TransactionType.SENT -> senderName
    TransactionType.RECEIVED -> receiverName
    TransactionType.ONLINE_PURCHASE -> senderName
}

private fun Transaction.otherParty(): String = when (type) {
    TransactionType.SENT -> receiverName
    TransactionType.RECEIVED -> senderName
    TransactionType.ONLINE_PURCHASE -> receiverName
}

private fun Transaction.transactionType(): TransactionTypeUiState = when (type) {
    TransactionType.SENT -> TransactionTypeUiState.SENT
    TransactionType.RECEIVED -> TransactionTypeUiState.RECEIVED
    TransactionType.ONLINE_PURCHASE -> TransactionTypeUiState.ONLINE_PURCHASE
}

private fun Transaction.transactionStatus(): TransactionStatusUiState = when (status) {
    TransactionStatus.SUCCESS -> TransactionStatusUiState.SUCCESS
    TransactionStatus.FAILED -> TransactionStatusUiState.FAILED
}

private fun Transaction.userInfo() = when (type) {
    TransactionType.SENT, TransactionType.ONLINE_PURCHASE -> Res.string.from
    TransactionType.RECEIVED -> Res.string.to
}

private fun Transaction.typeContent() = when (type) {
    TransactionType.SENT, TransactionType.RECEIVED -> Res.string.transfer
    TransactionType.ONLINE_PURCHASE -> Res.string.purchase
}

private fun Transaction.otherPartyTitle() = when (type) {
    TransactionType.SENT -> Res.string.to
    TransactionType.RECEIVED -> Res.string.from
    TransactionType.ONLINE_PURCHASE -> Res.string.receiver
}

private object Constants {
    const val ID_PREFIX = "TX-"
    const val ID_LENGTH = 6
    const val PNG_QUALITY = 100
}