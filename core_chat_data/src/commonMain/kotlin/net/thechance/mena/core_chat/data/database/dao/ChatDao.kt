package net.thechance.mena.core_chat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.thechance.mena.core_chat.data.database.entity.ChatEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatEntity WHERE id = :chatId")
    suspend fun getChat(chatId: String): ChatEntity?

    @Insert
    suspend fun insertChat(chat: ChatEntity)
}