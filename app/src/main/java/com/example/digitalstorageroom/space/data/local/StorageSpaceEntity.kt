package com.example.digitalstorageroom.space.data.local

import android.view.textclassifier.TextLanguage
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.Locale

enum class StorageSpaceType {
    FREEZER,
    REFRIGERATOR,
    NORMAL,
    OUTDOOR,
}

enum class Language(val locale: Locale) {
    GERMAN(Locale.GERMAN),
    ENGLISH(Locale.ENGLISH),
}

//TODO: there has to be a better way
fun StorageSpaceType.toString(language: Language = Language.ENGLISH) = when(this) {
    StorageSpaceType.FREEZER -> when(language) {
        Language.GERMAN -> "Gefrierschrank"
        Language.ENGLISH -> "Freezer"
    }
    StorageSpaceType.REFRIGERATOR -> when(language) {
        Language.GERMAN -> "Kühlschrank"
        Language.ENGLISH -> "Refrigerator"
    }
    StorageSpaceType.NORMAL -> when(language) {
        Language.GERMAN -> "Normal"
        Language.ENGLISH -> "Normal"

    }
    StorageSpaceType.OUTDOOR -> when(language) {
        Language.GERMAN -> "Im Freien"
        Language.ENGLISH -> "Outdoor"
    }

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