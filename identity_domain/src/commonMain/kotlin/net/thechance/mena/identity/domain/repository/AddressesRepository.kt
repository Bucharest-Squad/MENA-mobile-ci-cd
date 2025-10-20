package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.entity.Address
import net.thechance.mena.identity.domain.util.Coordinates
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AddressesRepository {
    suspend fun createAddress(address: Address)
    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAddress(addressId: Uuid, updated: Address)
    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteAddress(addressId: Uuid)
    suspend fun getUserAddresses(): List<Address>

    suspend fun getActiveAddress(): Address?
    @OptIn(ExperimentalUuidApi::class)
    suspend fun setActiveAddress(addressId: Uuid)
    suspend fun getCurrentLocation(): Coordinates?
    suspend fun getLocationName(coordinates: Coordinates): String
}