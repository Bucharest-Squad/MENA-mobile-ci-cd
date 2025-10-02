package net.thechance.mena.identity.domain.entity

data class User(
    val firstName:String,
    val lastName:String,
    val profileImageUrl:String,
    val username:String
)