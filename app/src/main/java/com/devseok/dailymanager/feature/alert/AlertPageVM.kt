package com.devseok.dailymanager.feature.alert

import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertPageVM @Inject constructor(
    private val repository: DailyManagerRepository
): ViewModel(), AlertPageBaseVM {

}