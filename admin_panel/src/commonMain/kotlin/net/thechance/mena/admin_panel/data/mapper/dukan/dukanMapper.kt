package net.thechance.mena.admin_panel.data.mapper.dukan

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.admin_panel.data.utils.orZero
import net.thechance.mena.admin_panel.data.remote.dto.dukan.CategoryDto
import net.thechance.mena.admin_panel.data.remote.dto.dukan.DukanDto
import net.thechance.mena.admin_panel.domain.entity.dukan.Category
import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan

fun DukanDto.toEntity() = Dukan(
    id = id,
    name = name.orEmpty(),
    address = address.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    latitude = latitude.orZero(),
    longitude = longitude.orZero(),
    categories = categories?.map(CategoryDto::toEntity).orEmpty(),
    date = date ?: LocalDateTime(2024, 11, 9, 14, 30, 0)
)

fun CategoryDto.toEntity() = Category(
    id = id,
    title = title.orEmpty()
)