package com.devseok.dailymanager.feature.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CalendarPage(
    navHostController: NavHostController,
    viewModel: CalendarPageBaseVM = hiltViewModel<CalendarPageVM>()
) {

    val configuration = LocalConfiguration.current

    BoxWithConstraints(
        modifier = Modifier
            .statusBarsPadding()
    ) {



    }
}