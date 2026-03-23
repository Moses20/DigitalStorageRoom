package com.example.digitalstorageroom.space.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageSpaceDao {

    @Query("Select * from storage_space")
    fun observeAll(): Flow<List<StorageSpaceEntity>>

    @Query("Select * from storage_space where id = :id")
    fun observeById(id: String): Flow<StorageSpaceEntity>

    @Query("Select * from storage_space where id = :id")
    suspend fun getById(id: String): StorageSpaceEntity?

    @Query("Select * from storage_space")
    suspend fun getAll(): List<StorageSpaceEntity>

    @Upsert
    suspend fun upsert(storageSpaceEntity: StorageSpaceEntity)

    @Upsert
    suspend fun upsertAll(storageSpaceEntities: List<StorageSpaceEntity>)

    @Query("Delete from storage_space where id = :id")
    suspend fun deleteById(id: String)
}
