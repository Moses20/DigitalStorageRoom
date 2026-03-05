package com.example.digitalstorageroom.data.storage

import java.time.OffsetDateTime

enum class StorageRoomType {
    FREEZER,
    REFRIGERATOR,
    NORMAL,
}

//TODO: Finde einen besseren Namen, es handelt sich um ein Item mit wenig Informationen ItemBase??


enum class CodeType{
    //https://www.scandit.com/resources/guides/types-of-barcodes-choosing-the-right-barcode/

    /*### EAN Code ###*/
    EAN_13,
    EAN_8,
    JAN_13,
    ISBN,
    ISSN,

    /*### QR Code ###*/
    QR

}

data class BarcodeItem (
    val barcode: String,
    val scanDate: OffsetDateTime = OffsetDateTime.now(),
    val type: CodeType = CodeType.EAN_13
)

data class StorageRoom(
    val title: String,
    val type: StorageRoomType,

)




