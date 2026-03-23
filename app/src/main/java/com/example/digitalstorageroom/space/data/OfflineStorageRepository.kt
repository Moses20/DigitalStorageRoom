package com.example.digitalstorageroom.space.data

import android.util.Log
import com.example.digitalstorageroom.space.data.local.StorageSpaceDao
import com.example.digitalstorageroom.space.data.local.StorageSpaceEntity
import com.example.digitalstorageroom.space.data.local.StorageSpaceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Singleton

@Singleton
class OfflineStorageRepository(
    private val storageSpaceDao: StorageSpaceDao
) : StorageSpaceRepository {
    override fun getStorageSpacesStream(): Flow<List<StorageSpace>> =
        //TODO Patrick, is withContext needed? see: https://github.com/android/architecture-samples/blob/main/app/src/main/java/com/example/android/architecture/blueprints/todoapp/data/DefaultTaskRepository.kt
        storageSpaceDao.observeAll().map(List<StorageSpaceEntity>::toModel)

    override fun getStorageSpaceStream(id: String): Flow<StorageSpace> =
        storageSpaceDao.observeById(id).map(StorageSpaceEntity::toModel)


    override suspend fun getStorageSpace(id: String): StorageSpace? =
        storageSpaceDao.getById(id)?.toModel()


    override suspend fun getStorageSpaces(): List<StorageSpace> =
        storageSpaceDao.getAll().toModel()


    override suspend fun addStorageSpace(title: String, type: StorageSpaceType) : StorageSpace {

        val id = UUID.randomUUID().toString()
        val storageSpace = StorageSpace(
            id = id,
            title = title,
            type = type
        )

        storageSpaceDao.upsert(storageSpace.toEntity())

        return storageSpace
    }

    override suspend fun updateStorageSpace(storageSpace: StorageSpace) {
        storageSpaceDao.getById(storageSpace.id)?.let {
            storageSpaceDao.upsert(storageSpace.toEntity(it))
        }
            //TODO Patrick: Create custom exception
            ?: throw Exception("StorageSpace with id ${storageSpace.id} not found")
    }

    override suspend fun deleteStorageSpace(id: String) {
        storageSpaceDao.deleteById(id)
    }

    override suspend fun synchronize() {
        Log.i(this::class.simpleName, "Does nothing!")
    }

    override suspend fun synchronizeStorageSpace(id: String) {
        Log.i(this::class.simpleName, "Does nothing!")
    }
}