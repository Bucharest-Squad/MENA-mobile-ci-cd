package net.thechance.mena.identity.presentation.screen.addresses.addEditLocation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SaveAddressStrategyFactory {
    @OptIn(ExperimentalUuidApi::class)
    fun createStrategy(addressId: Uuid?): ISaveAddress {
        return if (addressId == null) {
            CreateAddress()
        } else {
            UpdateAddress()
        }
    }
}
