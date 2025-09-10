package net.thechance.mena.core_chat.domain.exception

open class ChatException(logMessage: String, cause: Throwable? = null) : Exception(logMessage, cause)
class ContactSyncFailedException(logMessage: String, cause: Throwable? = null) : ChatException(logMessage, cause)
class GetUserContactsException(logMessage: String, cause: Throwable? = null) : ChatException(logMessage, cause)
class UnAuthorizedException(logMessage: String? = null) : ChatException(logMessage ?: "User is not authorized")