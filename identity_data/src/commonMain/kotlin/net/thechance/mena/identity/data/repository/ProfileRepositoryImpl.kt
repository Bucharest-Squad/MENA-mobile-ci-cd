package net.thechance.mena.identity.data.repository

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto

import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.data.mapper.toDto
import net.thechance.mena.identity.data.utils.getJson
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.data.utils.user
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.ProfileRepository


class ProfileRepositoryImpl(
    private val client: HttpClient,
    private val settings: Settings,
    private val json: Json = Json { encodeDefaults = true; ignoreUnknownKeys = true }
): ProfileRepository {


    override suspend fun fetchUserInfo(): UserInfo {
        safeWrapper {
            val userInfo: ProfileResponseDto = client.getJson(path =PROFILE)
            saveUserInfo(userInfo.toDomain())
        }
        return getUserInfoFromLocal()!!
    }

    override suspend fun getUserInfoFromLocal(): UserInfo?{
        val userJson = settings.user
        return userJson.let{ runCatching { json.decodeFromString<ProfileResponseDto>(it).toDomain()}.getOrNull() }
    }

    private fun saveUserInfo(userInfo: UserInfo){
        val userJson = json.encodeToString(userInfo.toDto())
        settings.user = userJson

    }


    companion object{
        const val PROFILE = "profile/me"
    }


}