package net.thechance.mena.trends.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import net.thechance.mena.trends.data.dto.ReelDto
import net.thechance.mena.trends.data.dto.ReelsListDto
import net.thechance.mena.trends.data.mapper.toEntity
import net.thechance.mena.trends.data.util.NetworkConstants.PAGE
import net.thechance.mena.trends.data.util.NetworkConstants.REELS
import net.thechance.mena.trends.data.util.NetworkConstants.TRENDS
import net.thechance.mena.trends.data.util.safeApiCall
import net.thechance.mena.trends.domain.entity.Reel
import net.thechance.mena.trends.domain.repository.ReelRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [ReelRepository::class])
class ReelsRepositoryImpl(
    @Provided private val httpClient: HttpClient
) : ReelRepository {

    override suspend fun deleteReelById(id: Int) = safeApiCall {}

    override suspend fun getAllReels(page: Int): List<Reel> =
        safeApiCall {
            val response: ReelsListDto<ReelDto> = httpClient.get("$TRENDS/$REELS") {
                parameter(PAGE, page)
            }.body()

            response.results.map { it.toEntity() }
        }
}