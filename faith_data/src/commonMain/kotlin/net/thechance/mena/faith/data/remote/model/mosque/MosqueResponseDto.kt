package net.thechance.mena.faith.data.remote.model.mosque

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MosqueResponseDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("address")
    val address: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("image")
    val image: String,
)
