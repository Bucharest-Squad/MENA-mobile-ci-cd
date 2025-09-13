package net.thechance.mena.identity.domain.exception

abstract class AuthenticationException(message: String) : Exception(message)

class InvalidCountryCodeException(
    countryCode: String
) : AuthenticationException("country code: $countryCode is not supported yet")

class InvalidMobileNumberException(
    mobileNumber: String
) : AuthenticationException("mobile number: $mobileNumber doesn't match validation")

class UserIsBlockedException(
    mobileNumber: String
) : AuthenticationException("user with mobile number: $mobileNumber has many login retries")

class InvalidPasswordException : AuthenticationException(
    "password doesn't match validations"
)

class InvalidCredentialsException(
    countryCode: String,
    mobileNumber: String
) : AuthenticationException(
    "user with mobile number: $countryCode$mobileNumber doesn't exist or password is incorrect"
)

class UserNeedsLoginException : AuthenticationException("user has no access to application")