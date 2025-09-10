package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException
import net.thechance.mena.core_chat.domain.exception.UnknownException

fun <T> BaseResponseDto<T>.getSuccessBodyOrThrow(): T? {
    return when {
        this.success -> this.body
        this.status == 401 -> throw UnAuthorizedException(this.message)
        else -> throw UnknownException(this.message)
    }
}