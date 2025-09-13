package net.thechance.mena.wallet.domain.repository

interface BalanceRepository {
    suspend fun getBalanceByUserId(userId: Int): Double
}