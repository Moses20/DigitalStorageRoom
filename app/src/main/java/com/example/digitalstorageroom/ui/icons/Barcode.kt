package com.example.digitalstorageroom.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Material.Barcode: ImageVector
    get() {
        if (_Barcode != null) {
            return _Barcode!!
        }
        _Barcode = ImageVector.Builder(
            name = "Barcode",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(40f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(560f)
                lineTo(40f, 760f)
                close()
                moveTo(160f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(-80f)
                close()
                moveTo(280f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(-40f)
                close()
                moveTo(400f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(-80f)
                close()
                moveTo(520f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(560f)
                lineTo(520f, 760f)
                close()
                moveTo(680f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(-40f)
                close()
                moveTo(800f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(560f)
                lineTo(800f, 760f)
                close()
            }
        }.build()

        return _Barcode!!
    }

@Suppress("ObjectPropertyName")
private var _Barcode: ImageVector? = null
