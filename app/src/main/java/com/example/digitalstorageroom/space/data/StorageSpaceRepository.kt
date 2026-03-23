package com.example.digitalstorageroom.space.data

import com.example.digitalstorageroom.space.data.local.StorageSpaceType
import kotlinx.coroutines.flow.Flow

interface StorageSpaceRepository {

    fun getStorageSpacesStream() : Flow<List<StorageSpace>>

    fun getStorageSpaceStream(id: String) : Flow<StorageSpace>

    suspend fun getStorageSpace(id: String) : StorageSpace?

    suspend fun getStorageSpaces() : List<StorageSpace>

    suspend fun addStorageSpace(title: String, type: StorageSpaceType) : StorageSpace

    suspend fun updateStorageSpace(storageSpace: StorageSpace)

    suspend fun deleteStorageSpace(id: String)

    //TODO how do we merge local and remote changes?
    suspend fun synchronize()

    suspend fun synchronizeStorageSpace(id: String)
}