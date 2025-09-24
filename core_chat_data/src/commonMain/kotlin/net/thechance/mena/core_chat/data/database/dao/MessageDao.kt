package net.thechance.mena.core_chat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.thechance.mena.core_chat.data.database.entity.MessageEntity
import net.thechance.mena.core_chat.data.database.entity.MessageStatus

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM MessageEntity ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM MessageEntity WHERE chat_id = :chatId ORDER BY timestamp ASC")
    fun getMessagesByChat(chatId: Long): Flow<List<MessageEntity>>

    @Query("UPDATE MessageEntity SET message_status = :status WHERE id = :id")
    suspend fun updateMessageStatus(id: Long, status: MessageStatus)
}