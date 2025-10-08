package net.thechance.mena.wallet.domain.repository

import net.thechance.mena.wallet.domain.model.PaymentConfirmation

interface PaymentRepository {
    suspend fun getPaymentConfirmation(receiverId: String, amount: Double): PaymentConfirmation
}