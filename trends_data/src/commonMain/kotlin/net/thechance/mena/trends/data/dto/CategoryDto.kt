package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id : String,
    @SerialName("name")
    val name : String,
    @SerialName("emoji")
    val emoji : String
)