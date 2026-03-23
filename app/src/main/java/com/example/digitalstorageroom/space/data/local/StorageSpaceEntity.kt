package com.example.digitalstorageroom.space.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

enum class StorageSpaceType {
    FREEZER,
    REFRIGERATOR,
    NORMAL,
}

interface Synchronizable {
    val lastSync: OffsetDateTime?
}

@Entity(
    tableName = "storage_space"
)
data class StorageSpaceEntity(
    //TODO use UUID
    @PrimaryKey val id: String,
    val title: String,
    val type: StorageSpaceType,
    val created: OffsetDateTime,
    val modified: OffsetDateTime,
    override val lastSync: OffsetDateTime? = null
) : Synchronizable