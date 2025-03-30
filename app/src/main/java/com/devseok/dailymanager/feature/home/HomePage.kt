package com.devseok.dailymanager.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomePage(
    navHostController: NavHostController,
    viewModel: HomePageBaseVM = hiltViewModel<HomePageVM>()
) {

    val scope = rememberCoroutineScope()



}