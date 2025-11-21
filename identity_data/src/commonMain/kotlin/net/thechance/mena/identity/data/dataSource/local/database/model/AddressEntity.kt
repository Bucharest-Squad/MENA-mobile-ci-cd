package net.thechance.mena.identity.data.dataSource.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Address")
data class AddressEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "address_line")
    val addressLine: String,
    @ColumnInfo(name = "address_type")
    val addressType: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)