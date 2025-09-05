package com.devseok.dailymanager.custom.picker

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * @author Ha Jin Seok
 * @created 2025-09-02
 * @desc
 */
@Immutable
public data class ColorEnvelopeDTO(
    val colorInt: Int = 0xFF3457EA.toInt(),
    val hexCode: String = "FF3457EA",
    val fromUser: Boolean = false,
) {
    val color: Color
        get() = Color(colorInt)
}