package com.devseok.dailymanager.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SettingPage(
    navHostController: NavHostController,
    viewModel: SettingPageBaseVM = hiltViewModel<SettingPageVM>()
) {

    val scope = rememberCoroutineScope()


}