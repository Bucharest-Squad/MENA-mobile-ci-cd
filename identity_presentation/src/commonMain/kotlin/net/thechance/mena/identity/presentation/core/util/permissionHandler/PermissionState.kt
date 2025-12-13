package net.thechance.mena.identity.presentation.core.util.permissionHandler

enum class PermissionState {
    NOT_DETERMINED,
    GRANTED,
    DENIED,
    DENIED_PERMANENTLY;

    fun isGranted(): Boolean {
        return this == GRANTED
    }
}