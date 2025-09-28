package net.thechance.mena.identity.data.dataSource.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import net.thechance.mena.identity.data.dataSource.local.database.model.UserEntity

@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(item: UserEntity)

    @Delete
    suspend fun delete(item: UserEntity)

    @Query("SELECT count(*) FROM User")
    suspend fun count(): Int

    @Query("SELECT username FROM User")
    fun getAllAsFlow(): Flow<List<String>>
}