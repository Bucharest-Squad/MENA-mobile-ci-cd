package net.thechance.mena.core_chat.data.messages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val senderId: String,
    val text: String,
    val timestamp: Long,
    val status: MessageStatus,
    val clientId: String,
    val serverId: String? = null
)

enum class MessageStatus {
    SENDING,
    SENT,
    DELIVERED,
    READ,
    FAILED
}

