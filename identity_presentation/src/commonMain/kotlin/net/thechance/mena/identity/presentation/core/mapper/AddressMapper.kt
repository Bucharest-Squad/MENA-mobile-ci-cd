package net.thechance.mena.identity.presentation.core.mapper

import net.thechance.mena.identity.domain.entity.Address
import net.thechance.mena.identity.domain.entity.AddressType
import net.thechance.mena.identity.domain.model.AddressInput
import net.thechance.mena.identity.presentation.feature.location.locationManagement.LocationManagementScreenUIState
import net.thechance.mena.identity.presentation.feature.location.shared.AddressUIState
import net.thechance.mena.identity.presentation.feature.location.shared.CoordinatesUiState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Address.toUiState(id: Uuid? = null, isMainAddress: Boolean = false) : AddressUIState = AddressUIState(
    id = id,
    addressType = addressType,
    isMainAddress = isMainAddress,
    addressDetails = addressLine,
    coordinates = CoordinatesUiState(latitude, longitude)
)

fun LocationManagementScreenUIState.AddEditAddressUIState.toAddressInput(): AddressInput = AddressInput(
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    addressLine = addressDetails,
    addressType = addressType ?: AddressType.Home,
)