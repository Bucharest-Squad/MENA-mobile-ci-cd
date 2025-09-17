package net.thechance.mena.trends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReelDto(
    @SerialName("reelId")
    val id : Int,
    @SerialName("thumbnailUrl")
    val reelImage : String,
    @SerialName("videoUrl")
    val reelUrl : String,
    val description : String,
    val createdAt : String,
    val likesCount : Int,
    val viewsCount : Int,
    val categories : List<CategoryDto>
)
