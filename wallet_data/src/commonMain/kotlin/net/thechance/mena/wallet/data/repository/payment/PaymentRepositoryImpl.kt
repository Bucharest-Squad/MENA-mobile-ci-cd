package net.thechance.mena.wallet.data.repository.payment

import io.ktor.client.request.parameter
import net.thechance.mena.wallet.data.dto.PaymentConfirmationDto
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.mapper.toEntity
import net.thechance.mena.wallet.data.network_client.NetworkClient
import net.thechance.mena.wallet.domain.model.PaymentConfirmation
import net.thechance.mena.wallet.domain.repository.PaymentRepository
import org.koin.core.annotation.Single

@Single
class PaymentRepositoryImpl(private val networkClient: NetworkClient): PaymentRepository {
    override suspend fun getPaymentConfirmation(
        receiverId: String,
        amount: Double
    ): PaymentConfirmation {
        return safeApiCall<PaymentConfirmationDto> {
            networkClient.post(CONFIRM_PAYMENT_PATH) {
                parameter(AMOUNT_PARAM, amount)
                parameter(RECEIVER_ID_PARAM, receiverId)
            }
        }.toEntity()
    }

    private companion object {
        const val PAYMENT_PATH = "/wallet/payment"
        const val CONFIRM_PAYMENT_PATH = "$PAYMENT_PATH/isValid"
        const val AMOUNT_PARAM = "amount"
        const val RECEIVER_ID_PARAM = "receiverId"
    }
}