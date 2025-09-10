package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.data.contacts.source.remote.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.ChatException
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException

fun <T> BaseResponseDto<T>.getSuccessBodyOrThrow(): T? {
    return when {
        this.success -> this.body
        this.status == 401 -> throw UnAuthorizedException(this.message)
        else -> throw ChatException(this.message ?: "Unknown error")
    }
}