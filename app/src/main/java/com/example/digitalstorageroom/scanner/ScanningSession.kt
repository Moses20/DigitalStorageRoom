package com.example.digitalstorageroom.scanner

import com.example.digitalstorageroom.data.storage.BarcodeItem
import com.example.digitalstorageroom.data.storage.Code
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.OffsetDateTime


class ScanningSession(
    val started: OffsetDateTime,
    var finished: OffsetDateTime,
    val itemsAndCount: MutableMap<Code, BarcodeItem>
)