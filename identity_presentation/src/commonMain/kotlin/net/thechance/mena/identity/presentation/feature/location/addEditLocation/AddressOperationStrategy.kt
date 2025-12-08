package net.thechance.mena.identity.presentation.feature.location.addEditLocation

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
interface AddressOperationStrategy {
    suspend fun execute(
        addressData: AddressData
    )
}