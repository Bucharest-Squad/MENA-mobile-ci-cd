package net.thechance.mena.admin_panel.domain.repository.dukan

import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import net.thechance.mena.admin_panel.domain.model.DukanQueryParams
import net.thechance.mena.admin_panel.domain.model.PagedResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface DukanRequestRepository {
    suspend fun getRequestedDukans(dukanQueryParams: DukanQueryParams?): PagedResult<Dukan>
    suspend fun getDukanById(dukanId: Uuid): Dukan
}