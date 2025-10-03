package net.thechance.mena.core_chat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.thechance.mena.core_chat.data.database.entity.MessageEntity
import net.thechance.mena.core_chat.data.database.entity.MessageStatus

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(message: List<MessageEntity>)

    @Query("SELECT * FROM MessageEntity WHERE chat_id = :chatId ORDER BY timestamp ASC")
    suspend fun getMessagesByChat(chatId: String): List<MessageEntity>

    @Query("UPDATE MessageEntity SET message_status = :status WHERE id = :id")
    suspend fun updateMessageStatus(id: String, status: MessageStatus)

    @Query("DELETE FROM MessageEntity WHERE id = :id")
    suspend fun deleteMessage(id: String)

}