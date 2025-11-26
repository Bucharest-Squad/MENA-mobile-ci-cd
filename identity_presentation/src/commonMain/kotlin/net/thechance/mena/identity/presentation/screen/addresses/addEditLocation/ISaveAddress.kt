package net.thechance.mena.identity.presentation.screen.addresses.addEditLocation

import net.thechance.mena.identity.domain.model.AddressInput
import net.thechance.mena.identity.domain.repository.AddressesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ISaveAddress {
    suspend fun saveAddress(
        repository: AddressesRepository,
        input: AddressInput,
        isMain: Boolean,
        addressId: Uuid?
    )
}