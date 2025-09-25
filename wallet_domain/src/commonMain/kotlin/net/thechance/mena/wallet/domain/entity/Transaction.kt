package net.thechance.mena.wallet.domain.entity

data class Transaction(
    val id: String,
    val createdAt: String,
    val amount: Double,
    val status: TransactionStatus?,
    val senderId: String,
    val senderName: String,
    val receiverId: String,
    val receiverName: String,
    val type : TransactionType

)
enum class TransactionType {
    SENT,
    RECEIVED,
    ONLINE_PURCHASE
}
enum class TransactionStatus{
    SUCCESS,
    FAIL
}