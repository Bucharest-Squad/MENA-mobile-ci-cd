package net.thechance.mena.admin_panel.data.local

import net.thechance.mena.admin_panel.data.remote.dto.DukanPagedResponse
import net.thechance.mena.admin_panel.data.remote.dto.dukan.DukanDto
import org.koin.core.annotation.Provided

@Provided
class InMemoryDukanDataStore {
    private val dukans = mutableMapOf<String, DukanDto>()

    fun storeDukans(dukanList: DukanPagedResponse<DukanDto>) {
        dukanList.content?.forEach { dukan ->
            dukans[dukan.id] = dukan
        }
    }

    fun getDukanById(dukanId: String): DukanDto? = dukans[dukanId]

    fun clear() = dukans.clear()
}
