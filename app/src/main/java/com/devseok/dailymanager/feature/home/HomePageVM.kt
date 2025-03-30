package com.devseok.dailymanager.feature.home

import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val repository: DailyManagerRepository
): ViewModel(), HomePageBaseVM{

}