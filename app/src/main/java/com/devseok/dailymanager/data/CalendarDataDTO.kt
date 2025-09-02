package com.devseok.dailymanager.data

import com.devseok.dailymanager.custom.picker.ColorEnvelopeDTO
import java.util.Date

data class CalendarDataDTO(
    val id: String = "", // Firestroe 문서 ID
    val userId: String = "",
    val date: String = "",
    val message: String = "",
    val color: ColorEnvelopeDTO = ColorEnvelopeDTO(),
    val timestamp: Date? = null  // 또는 Long?
)