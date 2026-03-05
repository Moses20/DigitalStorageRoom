package com.example.digitalstorageroom.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Material.HomeStorage: ImageVector
    get() {
        if (_HomeStorage != null) {
            return _HomeStorage!!
        }
        _HomeStorage = ImageVector.Builder(
            name = "HomeStorage",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveToRelative(200f, 840f)
                lineToRelative(-80f, -480f)
                horizontalLineToRelative(720f)
                lineToRelative(-80f, 480f)
                lineTo(200f, 840f)
                close()
                moveTo(267f, 760f)
                horizontalLineToRelative(426f)
                lineToRelative(51f, -320f)
                lineTo(216f, 440f)
                lineToRelative(51f, 320f)
                close()
                moveTo(400f, 600f)
                horizontalLineToRelative(160f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(600f, 560f)
                quadToRelative(0f, -17f, -11.5f, -28.5f)
                reflectiveQuadTo(560f, 520f)
                lineTo(400f, 520f)
                quadToRelative(-17f, 0f, -28.5f, 11.5f)
                reflectiveQuadTo(360f, 560f)
                quadToRelative(0f, 17f, 11.5f, 28.5f)
                reflectiveQuadTo(400f, 600f)
                close()
                moveTo(240f, 320f)
                quadToRelative(-17f, 0f, -28.5f, -11.5f)
                reflectiveQuadTo(200f, 280f)
                quadToRelative(0f, -17f, 11.5f, -28.5f)
                reflectiveQuadTo(240f, 240f)
                horizontalLineToRelative(480f)
                quadToRelative(17f, 0f, 28.5f, 11.5f)
                reflectiveQuadTo(760f, 280f)
                quadToRelative(0f, 17f, -11.5f, 28.5f)
                reflectiveQuadTo(720f, 320f)
                lineTo(240f, 320f)
                close()
                moveTo(320f, 200f)
                quadToRelative(-17f, 0f, -28.5f, -11.5f)
                reflectiveQuadTo(280f, 160f)
                quadToRelative(0f, -17f, 11.5f, -28.5f)
                reflectiveQuadTo(320f, 120f)
                horizontalLineToRelative(320f)
                quadToRelative(17f, 0f, 28.5f, 11.5f)
                reflectiveQuadTo(680f, 160f)
                quadToRelative(0f, 17f, -11.5f, 28.5f)
                reflectiveQuadTo(640f, 200f)
                lineTo(320f, 200f)
                close()
                moveTo(267f, 760f)
                horizontalLineToRelative(426f)
                horizontalLineToRelative(-426f)
                close()
            }
        }.build()

        return _HomeStorage!!
    }

@Suppress("ObjectPropertyName")
private var _HomeStorage: ImageVector? = null
