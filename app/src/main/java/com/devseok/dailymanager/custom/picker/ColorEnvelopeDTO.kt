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
    val colorInt: Int = 0xFFFFFFFF.toInt(),
    val hexCode: String = "FFFFFFFF",
    val fromUser: Boolean = false,
) {
    val color: Color
        get() = Color(colorInt)
}