package com.devseok.dailymanager.feature.main

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.devseok.dailymanager.R
import com.devseok.dailymanager.commons.LockScreenOrientation
import com.devseok.dailymanager.feature.bottom.BottomNavigation
import com.devseok.dailymanager.navigation.NavGraph


@Composable
fun MainPage(
    viewModel: MainPageBaseVM = hiltViewModel<MainPageVM>()
) {
    // 세로 영역 고정
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background)),
        bottomBar = {
            BottomNavigation(navHostController = navController)
        }
    ) {
        Box(
          modifier = Modifier
              .fillMaxSize()
              .padding(it)
              .background(colorResource(R.color.background))
        ) {
            NavGraph(navHostController = navController)
        }
    }
}