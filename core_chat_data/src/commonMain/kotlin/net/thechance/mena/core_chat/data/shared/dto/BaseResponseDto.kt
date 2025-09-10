package net.thechance.mena.core_chat.data.shared.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponseDto<T>(
    @SerialName("body")
    val body: T? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("success")
    val success: Boolean = false,
    @SerialName("message")
    val message: String? = null
)