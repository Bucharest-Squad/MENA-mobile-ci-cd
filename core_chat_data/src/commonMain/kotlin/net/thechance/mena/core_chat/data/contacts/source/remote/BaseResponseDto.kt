package net.thechance.mena.core_chat.data.contacts.source.remote

open class BaseResponseDto<T>(
    val body: T? = null,
    val status: Int,
    val success: Boolean,
    val message: String? = null
)