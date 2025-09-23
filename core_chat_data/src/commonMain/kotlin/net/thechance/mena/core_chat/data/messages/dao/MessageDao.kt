package net.thechance.mena.core_chat.data.messages.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import net.thechance.mena.core_chat.data.messages.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM MessageEntity ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM MessageEntity WHERE senderId = :senderId ORDER BY timestamp ASC")
    fun getMessagesBySender(senderId: String): Flow<List<MessageEntity>>

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Query("SELECT * FROM MessageEntity WHERE clientId = :clientId")
    suspend fun getMessageByClientId(clientId: String): MessageEntity?
}