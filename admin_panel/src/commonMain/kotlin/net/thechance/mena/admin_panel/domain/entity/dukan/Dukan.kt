package net.thechance.mena.admin_panel.domain.entity.dukan

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Dukan(
    val id: Uuid,
    val name: String,
    val imageUrl: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val categories: List<Category>,
    val activationStatus: ActivationStatus,
    val status: Status
) {
    enum class ActivationStatus {
        ACTIVATED,
        DEACTIVATED,
    }

    enum class Status {
        APPROVED,
        REJECTED,
        PENDING
    }
}