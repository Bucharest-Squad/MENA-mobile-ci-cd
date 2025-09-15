package net.thechance.mena.identity.data.datasource

import com.russhwolf.settings.Settings
import net.thechance.mena.identity.data.datautils.DataStoreConstants
import net.thechance.mena.identity.data.datautils.DataStoreConstants.ACCESS_TOKEN
import net.thechance.mena.identity.data.datautils.DataStoreConstants.REFRESH_TOKEN

class TokenManager(
    private val settings: Settings = Settings()
) {

    fun saveAccessToken(accessToken: String) {
        settings.putString(ACCESS_TOKEN, accessToken)
    }

    fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    fun saveRefreshToken(refreshToken: String) {
        settings.putString(REFRESH_TOKEN, refreshToken)
    }

    fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN, "")
    }

    fun clearTokens() {
        settings.clear()
    }
}
