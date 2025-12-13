package net.thechance.mena.identity.presentation.feature.location.locationManagement

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
interface AddressOperationStrategy {
    suspend fun execute(
        addressData: AddressData
    )
}