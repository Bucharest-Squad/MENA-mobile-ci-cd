package net.thechance.mena.core_chat.data.database

import androidx.room.TypeConverter
import net.thechance.mena.core_chat.data.database.entity.MessageEntity

class MessageStatusConverter {

    @TypeConverter
    fun fromMessageStatus(status: MessageEntity.MessageStatus): String = status.name

    @TypeConverter
    fun toMessageStatus(status: String): MessageEntity.MessageStatus = runCatching { MessageEntity.MessageStatus.valueOf(status) }.getOrDefault(
        MessageEntity.MessageStatus.FAILED)

}