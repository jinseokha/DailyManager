package com.devseok.dailymanager.feature.setting

import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingPageVM @Inject constructor(
    private val repository: DailyManagerRepository
): ViewModel(), SettingPageBaseVM {

}