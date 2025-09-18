package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReelDto(
    @SerialName("reelId")
    val id : String,
    @SerialName("thumbnailUrl")
    val reelImageUrl : String,
    @SerialName("videoUrl")
    val videoUrl : String,
    @SerialName("description")
    val description : String,
    @SerialName("createdAt")
    val createdAt : String,
    @SerialName("likesCount")
    val likesCount : Int,
    @SerialName("viewsCount")
    val viewsCount : Int,
    @SerialName("categories")
    val categories : List<CategoryDto>
)
