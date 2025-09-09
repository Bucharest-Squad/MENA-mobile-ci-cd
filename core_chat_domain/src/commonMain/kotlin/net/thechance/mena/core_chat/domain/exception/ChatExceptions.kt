package net.thechance.mena.core_chat.domain.exception

open class ChatExceptions(logMessage: String, cause: Throwable? = null) : Exception(logMessage, cause)
class ContactsException(logMessage: String, cause: Throwable? = null) : ChatExceptions(logMessage, cause)
class UnAuthorizedException(logMessage: String? = null) : ChatExceptions(logMessage ?: "User is not authorized")