package net.thechance.mena.core_chat.domain.exception

abstract class ChatExceptions(logMessage: String, cause: Throwable?) : Exception(logMessage, cause)
class FailException(logMessage: String, cause: Throwable? = null) : ChatExceptions(logMessage, cause)