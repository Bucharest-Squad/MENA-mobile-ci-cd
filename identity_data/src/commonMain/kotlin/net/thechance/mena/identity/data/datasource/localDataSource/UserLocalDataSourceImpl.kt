package net.thechance.mena.identity.data.datasource.localDataSource

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thechance.mena.identity.domain.model.UserInfo

class UserLocalDataSourceImpl(
    private val settings: Settings
) : UserLocalDataSource {
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

    override fun saveUserInfo(userInfo: UserInfo) {
        settings.putString(FIRSTNAME, userInfo.firstName)
        settings.putString(LASTNAME, userInfo.lastName)
        settings.putString(PROFILE_IMAGE_URL, userInfo.profileImageUrl)
        settings.putString(USERNAME, userInfo.username)
    }

    override fun getUserInfo(): UserInfo?=
        UserInfo(
                settings.getString(FIRSTNAME, ""),
                settings.getString(LASTNAME, ""),
                settings.getString(PROFILE_IMAGE_URL, ""),
                settings.getString(USERNAME, "")
            )



    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val FIRSTNAME = "first_name"
        const val LASTNAME = "last_name"
        const val PROFILE_IMAGE_URL = "profile_image_url"
        const val USERNAME = "username"
    }
}
