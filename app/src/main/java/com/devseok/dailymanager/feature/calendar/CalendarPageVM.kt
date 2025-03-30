package com.devseok.dailymanager.feature.calendar

import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarPageVM @Inject constructor(
    private val repository: DailyManagerRepository
): ViewModel(), CalendarPageBaseVM {

}