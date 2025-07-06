package com.devseok.dailymanager.data

import com.google.firebase.firestore.FieldValue

data class CalendarData(
    val userId: String = "",
    val date: String = "",
    val message: String = "",
    val timestamp: FieldValue = FieldValue.serverTimestamp()
)