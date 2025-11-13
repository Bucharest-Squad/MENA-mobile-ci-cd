package net.thechance.mena.admin_panel.data.repository.dukan

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import net.thechance.mena.admin_panel.domain.model.DukanQueryParams
import net.thechance.mena.admin_panel.domain.model.PagedResult
import net.thechance.mena.admin_panel.domain.repository.dukan.DukanRequestRepository
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Single
class DukanRequestRepositoryImpl : DukanRequestRepository {
    override suspend fun getRequestedDukans(dukanQueryParams: DukanQueryParams?): PagedResult<Dukan> {
        return PagedResult(
            items = listOf(
                Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                ),
                Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                ), Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                ),
                Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                ),
                Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                ),
                Dukan(
                    id = "7",
                    name = "Baghdad",
                    imageUrl = "",
                    address = "Baghdad mall, Baghdad",
                    date = LocalDateTime(2024, 11, 9, 14, 30, 0),
                    latitude = 77.7,
                    longitude = 77.7,
                    categories = emptyList()
                )
            ),
            totalPages = 10,
            currentPage = 0
        )
    }

    override suspend fun getDukanById(dukanId: Uuid): Dukan {
        TODO("Not yet implemented")
    }
}