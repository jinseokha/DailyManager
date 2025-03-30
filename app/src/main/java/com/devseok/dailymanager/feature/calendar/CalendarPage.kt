package com.devseok.dailymanager.feature.calendar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun CalendarPage(
    navHostController: NavHostController,
    viewModel: CalendarPageBaseVM = hiltViewModel<CalendarPageVM>()
) {

}