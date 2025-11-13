package net.thechance.mena.admin_panel.data.mapper.dukan

import net.thechance.mena.admin_panel.data.mapper.toUuidOrNull
import net.thechance.mena.admin_panel.data.utils.orZero
import net.thechance.mena.admin_panel.data.remote.dto.dukan.CategoryDto
import net.thechance.mena.admin_panel.data.remote.dto.dukan.DukanDto
import net.thechance.mena.admin_panel.domain.entity.dukan.Category
import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun DukanDto.toEntity() = Dukan(
    id = id.toUuidOrNull() ?: throw IllegalStateException("Invalid User id"),
    name = name.orEmpty(),
    address = address.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    latitude = latitude.orZero(),
    longitude = longitude.orZero(),
    categories = categories?.map(CategoryDto::toEntity).orEmpty(),
    activationStatus = when (activationStatus?.uppercase()) {
        "ACTIVED" -> Dukan.ActivationStatus.ACTIVATED
        "DEACTIVATED" -> Dukan.ActivationStatus.DEACTIVATED
        else -> Dukan.ActivationStatus.DEACTIVATED
    },
    status = when (status?.uppercase()) {
        "APPROVED" -> Dukan.Status.APPROVED
        "REJECTED" -> Dukan.Status.REJECTED
        "PENDING" -> Dukan.Status.PENDING
        else -> Dukan.Status.PENDING
    }
)

fun CategoryDto.toEntity() = Category(
    id = id,
    title = title.orEmpty()
)