package net.thechance.mena.core_chat.domain.exception

abstract class ChatExceptions(logMessage: String, cause: Throwable? = null) : Exception(logMessage, cause)
class ContactsException(logMessage: String, cause: Throwable? = null) : ChatExceptions(logMessage, cause)