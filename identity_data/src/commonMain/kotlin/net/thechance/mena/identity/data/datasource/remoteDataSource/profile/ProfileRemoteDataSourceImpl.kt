package net.thechance.mena.identity.data.datasource.remoteDataSource.profile

import io.ktor.client.HttpClient
import net.thechance.mena.identity.data.datasource.utils.getJson
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto

class ProfileRemoteDataSourceImpl(
    val client: HttpClient
): ProfileRemoteDataSource {

    override suspend fun getUserInfo(): ProfileResponseDto {
       return client.getJson(PROFILE)
    }

    companion object {
        const val PROFILE = "user/profile"
    }
}