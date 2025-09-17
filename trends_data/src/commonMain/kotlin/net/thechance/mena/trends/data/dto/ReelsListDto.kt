package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReelsListDto<T>(
    val page : Int,
    val results: List<T>,
    @SerialName("total_results")
    val totalResults: Int
)
