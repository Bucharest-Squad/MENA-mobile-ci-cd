package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dto.addresses.AddressRequestDto
import net.thechance.mena.identity.data.dto.addresses.AddressResponseDto
import net.thechance.mena.identity.domain.entity.Address
import net.thechance.mena.identity.domain.entity.AddressType.AddressTypeMapper.getAddressType
import net.thechance.mena.identity.domain.entity.AddressType.AddressTypeMapper.getAddressTypeFromString

fun Address.toDto(id: String? = null, isActive: Boolean = false): AddressRequestDto {
    return AddressRequestDto(
        id = id,
        latitude = latitude,
        longitude = longitude,
        addressType = addressType.getAddressType(),
        addressLine = addressLine,
        isActive = isActive
    )
}

fun AddressResponseDto.toEntity(): Address {
    return Address(
        latitude = latitude,
        longitude = longitude,
        addressType = getAddressTypeFromString(addressType),
        addressLine = addressLine
    )
}