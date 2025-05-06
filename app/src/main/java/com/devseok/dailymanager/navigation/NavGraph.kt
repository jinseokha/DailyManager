package com.devseok.dailymanager.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devseok.dailymanager.feature.alert.AlertPage
import com.devseok.dailymanager.feature.calendar.CalendarPage
import com.devseok.dailymanager.feature.login.LoginPage
import com.devseok.dailymanager.feature.login.LoginPageVM
import com.devseok.dailymanager.feature.setting.SettingPage
import com.devseok.dailymanager.feature.splash.SplashPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    viewModel: LoginPageVM = hiltViewModel()
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val userInfo by viewModel.fireBaseUserInfo.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Route.SplashPage.routeName
    ) {
        composable(Route.SplashPage.routeName) {
            SplashPage(
                navHostController = navController,
                onNavigate = {
                    if (userInfo != null) {
                        navController.navigate(Route.CalendarPage.routeName) {
                            popUpTo(Route.CalendarPage.routeName) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Route.LoginPage.routeName) {
                            popUpTo(Route.LoginPage.routeName) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Route.LoginPage.routeName) {
            LoginPage(
                navHostController = navController,
            )
        }

        composable(Route.CalendarPage.routeName) {
            CalendarPage(
                navHostController = navController,
                drawerState = drawerState
            )
        }

        composable(Route.SettingPage.routeName) {
            SettingPage(navHostController = navController)
        }

        composable(Route.AlertPage.routeName) {
            AlertPage(navHostController = navController)
        }

    }

}