package com.devseok.dailymanager.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devseok.dailymanager.feature.login.LoginPageVM
import com.devseok.dailymanager.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashPage(
    navHostController: NavHostController,
    viewModel: LoginPageVM = hiltViewModel<LoginPageVM>(),
    onNavigate: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(1500) // 예: 로딩 중 애니메이션
        onNavigate()
    }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "splash login",
            fontSize = 25.sp
        )
    }
}