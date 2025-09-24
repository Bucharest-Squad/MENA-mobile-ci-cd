@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package net.thechance.mena.core_chat.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import net.thechance.mena.core_chat.data.database.dao.ChatDao
import net.thechance.mena.core_chat.data.database.dao.MessageDao
import net.thechance.mena.core_chat.data.database.entity.ChatEntity
import net.thechance.mena.core_chat.data.database.entity.MessageEntity

@Database(entities = [MessageEntity::class, ChatEntity::class], version = 1)
@ConstructedBy(ChatDatabaseConstructor::class)
@TypeConverters(MessageStatusConverter::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao
    abstract fun getChatDao(): ChatDao
}

@Suppress("KotlinNoActualForExpect")
expect object ChatDatabaseConstructor : RoomDatabaseConstructor<ChatDatabase> {
    override fun initialize(): ChatDatabase
}