package net.thechance.mena.wallet.data.repository.payment

import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import net.thechance.mena.wallet.data.dto.PaymentConfirmationDto
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.mapper.toEntity
import net.thechance.mena.wallet.data.network_client.NetworkClient
import net.thechance.mena.wallet.domain.model.PaymentConfirmation
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.repository.PaymentRepository
import org.koin.core.annotation.Single

@Single
class PaymentRepositoryImpl(private val networkClient: NetworkClient): PaymentRepository {
    override suspend fun getPaymentConfirmation(
        receiverId: String,
        amount: Double
    ): PaymentConfirmation {
        val requestBody = PaymentConfirmationRequest(receiverId, amount)
        return safeApiCall<PaymentConfirmationDto> {
            networkClient.post(CONFIRM_PAYMENT_PATH) {
                setBody(requestBody)
            }
        }.toEntity()

//        PaymentConfirmation(
//            balance = 500045.0102,
//            receiverName = "nour",
//            receiverImg = "https://media.istockphoto.com/id/469738422/photo/large-boulders-on-lake-shore-at-sunset-minnesota-usa.jpg?s=612x612&w=0&k=20&c=4FzViDygZ8CgixTqt3VOudLJUP8uoSeh2UlD_qHYkAw=",
//            status = TransactionStatus.SUCCESS
//        )

    }

    data class PaymentConfirmationRequest(
        val receiverId: String,
        val amount: Double
    )

    private companion object {
        const val PAYMENT_PATH = "/wallet/payment"
        const val CONFIRM_PAYMENT_PATH = "$PAYMENT_PATH/isValid"
        const val AMOUNT_PARAM = "amount"
        const val RECEIVER_ID_PARAM = "receiverId"
    }
}