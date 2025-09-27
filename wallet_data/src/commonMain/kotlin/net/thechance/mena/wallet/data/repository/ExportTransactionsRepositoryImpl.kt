package net.thechance.mena.wallet.data.repository

import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.extension.NetworkClient
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import org.koin.core.annotation.Single

@Single
class ExportTransactionsRepositoryImpl(
    private val networkClient: NetworkClient
) : ExportTransactionsRepository {
    override suspend fun getFilteredTransactionsFile(
        type: Set<Transaction.Type>?,
        status: Transaction.Status?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): ByteArray {
        return safeApiCall {
            networkClient.post(TRANSACTIONS_PATH)
        }
    }

    override suspend fun getAllTransactionsFile(): ByteArray {
//        return safeApiCall {
//            networkClient.post(TRANSACTIONS_PATH)
//        }
        return generateFakePdfFile()
    }

    private companion object {
        const val TRANSACTIONS_PATH = ""
    }

    private fun generateFakePdfFile(): ByteArray {
        val fakeContent = """
            %PDF-1.4
            % Fake PDF File
            1 0 obj
            << /Type /Catalog /Pages 2 0 R >>
            endobj
            2 0 obj
            << /Type /Pages /Count 1 /Kids [3 0 R] >>
            endobj
            3 0 obj
            << /Type /Page /Parent 2 0 R /MediaBox [0 0 300 144] /Contents 4 0 R >>
            endobj
            4 0 obj
            << /Length 44 >>
            stream
            BT /F1 24 Tf 100 100 Td (Hello Fake PDF) Tj ET
            endstream
            endobj
            xref
            0 5
            0000000000 65535 f
            0000000010 00000 n
            0000000060 00000 n
            0000000110 00000 n
            0000000175 00000 n
            trailer
            << /Root 1 0 R /Size 5 >>
            startxref
            250
            %%EOF
        """.trimIndent()
        return fakeContent.toByteArray()
    }
}