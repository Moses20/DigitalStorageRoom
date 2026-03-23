package com.example.digitalstorageroom.space.data

import com.example.digitalstorageroom.space.data.local.StorageSpaceEntity
import java.time.OffsetDateTime


fun StorageSpaceEntity.toModel() = StorageSpace(
    id = this.id,
    title = this.title,
    type = this.type
)

fun List<StorageSpaceEntity>.toModel() = map(StorageSpaceEntity::toModel)

fun StorageSpace.toEntity() = StorageSpaceEntity(
    id = this.id,
    title = this.title,
    type = this.type,
    created = OffsetDateTime.now(),
    modified = OffsetDateTime.now(),
    lastSync = null,
)

fun StorageSpace.toEntity(spaceEntity: StorageSpaceEntity) = StorageSpaceEntity(
    id = spaceEntity.id,
    title = this.title,
    type = this.type,
    created = spaceEntity.created,
    modified = OffsetDateTime.now(),

)