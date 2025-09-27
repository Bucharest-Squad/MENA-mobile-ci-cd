package net.thechance.mena.identity.data.datasource.localDataSource

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import net.thechance.mena.identity.domain.model.UserInfo
import kotlinx.coroutines.flow.flow
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.data.mapper.toDto

class UserLocalDataSourceImpl(
    private val settings: Settings
) : UserLocalDataSource {

    private val json = Json { ignoreUnknownKeys = true }


    override fun saveAccessToken(accessToken: String) {
        settings.putString(ACCESS_TOKEN, accessToken)
    }

    override fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    override fun saveRefreshToken(refreshToken: String) {
        settings.putString(REFRESH_TOKEN, refreshToken)
    }

    override fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN, "")
    }


    override fun saveUserInfo(user: UserInfo) {
        val userJson = json.encodeToString(user.toDto())
        settings.putString(USER_KEY , userJson)
    }

    override fun getUserInfo(): UserInfo? {
        val userJson = settings.getStringOrNull(USER_KEY)
        return userJson?.let{ runCatching { json.decodeFromString<ProfileResponseDto>(it).toDomain()}.getOrNull() }
    }



    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_KEY = "user"

    }
}
