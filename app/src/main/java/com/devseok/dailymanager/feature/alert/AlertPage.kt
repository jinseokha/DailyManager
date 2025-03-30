package com.devseok.dailymanager.feature.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun AlertPage(
    navHostController: NavHostController,
    viewModel: AlertPageBaseVM = hiltViewModel<AlertPageVM>()
) {

    val scope = rememberCoroutineScope()


}