package net.thechance.mena.core_chat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "chat_image_url") val imageUrl: String?,
    @ColumnInfo(name = "chat_name") val chatName: String
)