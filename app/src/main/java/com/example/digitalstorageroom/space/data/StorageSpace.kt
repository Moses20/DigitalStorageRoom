package com.example.digitalstorageroom.space.data

import com.example.digitalstorageroom.space.data.local.StorageSpaceType
import java.time.OffsetDateTime

data class StorageSpace(
    val id: String,
    val title: String,
    val type: StorageSpaceType,
)
