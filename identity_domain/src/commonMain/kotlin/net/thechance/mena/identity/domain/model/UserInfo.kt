package net.thechance.mena.identity.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class UserInfo(

    val firstName:String,
    val lastName:String,
    val profileImageUrl:String,
    val username:String

)
