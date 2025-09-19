package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteResponse<T>(
    @SerialName("pageNumber")
    val pageNumber : Int,
    @SerialName("results")
    val results: List<T>,
    @SerialName("totalResults")
    val totalResults: Int
)
