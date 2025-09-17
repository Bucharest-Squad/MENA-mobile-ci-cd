package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id : Int,
    val name : String,
    @SerialName("emoji")
    val iconPath : String
)