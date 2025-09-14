package net.thechance.mena.wallet.domain.repository

interface BalanceRepository {
    suspend fun getBalance(userId: Int): Double
}