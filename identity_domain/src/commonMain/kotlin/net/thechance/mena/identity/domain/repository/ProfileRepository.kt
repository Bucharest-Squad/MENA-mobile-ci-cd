package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.model.UserInfo

interface ProfileRepository {

    suspend fun getUserInfo():UserInfo

}