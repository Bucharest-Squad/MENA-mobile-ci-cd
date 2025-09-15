package net.thechance.mena.identity.domain.exception

sealed class IdentityException() : Exception()
class NetworkException() : IdentityException()
class UnauthorizedException() : IdentityException()
class InvalidDataException() : IdentityException()
class UnknownException() : IdentityException()
