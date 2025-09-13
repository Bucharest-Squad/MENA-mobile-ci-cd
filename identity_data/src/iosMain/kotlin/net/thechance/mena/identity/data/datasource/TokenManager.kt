package net.thechance.mena.identity.data.datasource

import platform.Foundation.NSUserDefaults

actual class TokenManager(
    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults
)  {
    actual suspend fun saveAccessToken(accessToken: String) {
        defaults.setObject(accessToken, forKey = "USER_ACCESS_TOKEN")
    }
    actual suspend fun getAccessToken(): String {
        return defaults.stringForKey("USER_ACCESS_TOKEN") ?: ""
    }

    actual suspend fun saveRefreshToken(refreshToken: String) {
        defaults.setObject(refreshToken, forKey = "REFRESH_TOKEN")
    }

    actual suspend fun getRefreshToken(): String {
        return defaults.stringForKey("REFRESH_TOKEN") ?: ""
    }

    actual suspend fun clearTokens() {
        defaults.removeObjectForKey("USER_ACCESS_TOKEN")
        defaults.removeObjectForKey("REFRESH_TOKEN")
    }
}

