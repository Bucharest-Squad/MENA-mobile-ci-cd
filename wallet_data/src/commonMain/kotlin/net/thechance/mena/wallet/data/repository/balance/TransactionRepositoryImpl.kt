package net.thechance.mena.wallet.data.repository.balance

import io.ktor.client.request.parameter
import io.ktor.http.Parameters
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import net.thechance.mena.wallet.data.dto.PagedTransactionResponseDto
import net.thechance.mena.wallet.data.dto.TransactionDto
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.mapper.toEntity
import net.thechance.mena.wallet.data.mapper.toParameters
import net.thechance.mena.wallet.data.network_client.NetworkClient
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionFilterParams
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.model.TransactionType
import net.thechance.mena.wallet.domain.repository.TransactionRepository
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Single
class TransactionRepositoryImpl(
    private val networkClient: NetworkClient
) : TransactionRepository {
    override suspend fun getTransactionHistory(transactionFilterParams: TransactionFilterParams?): List<Transaction> {
        return safeApiCall<PagedTransactionResponseDto> {
            networkClient.get("$TRANSACTION_PATH?${transactionFilterParams?.toParameters()}")
        }.transactions.orEmpty().map { it.toEntity() }

    }

    override suspend fun getAllTransaction(): List<Transaction> {
        return listOf(
            Transaction(
                id = Uuid.parse(""),
                createdAt = LocalDateTime(
                    date = LocalDate(2025, 8, 20),
                    time = LocalTime(12, 0)
                ),
                amount = 5000.0,
                status = TransactionStatus.SUCCESS,
                senderName = "Nour Elhoda",
                receiverName = "Nour Elhoda",
                type = TransactionType.RECEIVED
            )
        )
    }

    override suspend fun getTransactionById(transactionId: Uuid): Transaction {
        return Transaction(
            id = Uuid.parse(""),
            createdAt = LocalDateTime(
                date = LocalDate(2025, 8, 20),
                time = LocalTime(12, 0)
            ),
            amount = 5000.0,
            status = TransactionStatus.SUCCESS,
            senderName = "Nour Elhoda",
            receiverName = "Nour Elhoda",
            type = TransactionType.RECEIVED
        )
    }

    private companion object {
        const val TRANSACTION_PATH = "wallet/transactions"
    }

}