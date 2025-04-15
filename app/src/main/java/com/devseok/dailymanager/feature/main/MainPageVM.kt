package com.devseok.dailymanager.feature.main

import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainPageVM @Inject constructor(
    private val repository: DailyManagerRepository
): ViewModel(), MainPageBaseVM {

}