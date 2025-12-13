package net.thechance.mena.identity.presentation.core.util

import net.thechance.mena.identity.domain.entity.AddressType
import net.thechance.mena.identity.presentation.feature.location.locationManagement.LocationManagementScreenUIState
import kotlin.uuid.ExperimentalUuidApi

fun isAddressInputValid(addressType: AddressType?, otherAddressType: String?): Boolean {
    return when (addressType) {
        AddressType.Home -> true
        AddressType.Office -> true
        is AddressType.Other -> !otherAddressType.isNullOrBlank()
        else -> false
    }
}


fun LocationManagementScreenUIState.isAddressInputValid(): Boolean {
    return isAddressInputValid(
        addressUIState.addressType,
        addressUIState.otherAddressType
    )
}

fun LocationManagementScreenUIState.hasAddressChanged(): Boolean {
    return addressUIState.addressDetails != originalAddressUIState.addressDetails ||
            addressUIState.addressType != originalAddressUIState.addressType ||
            addressUIState.otherAddressType != originalAddressUIState.otherAddressType ||
            addressUIState.coordinates != originalAddressUIState.coordinates
}

@OptIn(ExperimentalUuidApi::class)
fun LocationManagementScreenUIState.isSaveEnabled(): Boolean {
    val isEditMode = addressUIState.addressID != null
    
    return if (isEditMode) {
        hasAddressChanged() && isAddressInputValid()
    } else {
        addressUIState.addressDetails.isNotBlank() && isAddressInputValid()
    }
}

