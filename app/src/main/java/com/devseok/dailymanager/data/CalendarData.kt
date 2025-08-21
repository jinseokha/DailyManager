package com.devseok.dailymanager.data

import com.google.firebase.firestore.FieldValue
import java.util.Date

data class CalendarData(
    val id: String = "" , // Firestroe 문서 ID
    val userId: String = "",
    val date: String = "",
    val message: String = "",
    val timestamp: Date? = null // 또는 Long?
)