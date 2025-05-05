package com.devseok.dailymanager.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devseok.dailymanager.feature.alert.AlertPage
import com.devseok.dailymanager.feature.calendar.CalendarPage
import com.devseok.dailymanager.feature.setting.SettingPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    drawerState: DrawerState
) {

    NavHost(
        navController = navHostController,
        startDestination = Route.CalendarPage.routeName
    ) {

        composable(Route.CalendarPage.routeName) {
            CalendarPage(
                navHostController = navHostController,
                drawerState = drawerState
            )
        }

        composable(Route.SettingPage.routeName) {
            SettingPage(navHostController = navHostController)
        }

        composable(Route.AlertPage.routeName) {
            AlertPage(navHostController = navHostController)
        }

    }

}