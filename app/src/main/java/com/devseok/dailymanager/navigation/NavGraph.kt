package com.devseok.dailymanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devseok.dailymanager.feature.alert.AlertPage
import com.devseok.dailymanager.feature.calendar.CalendarPage
import com.devseok.dailymanager.feature.home.HomePage
import com.devseok.dailymanager.feature.home.HomePageVM
import com.devseok.dailymanager.feature.setting.SettingPage

@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Route.HomePage.routeName
    ) {
        composable(Route.HomePage.routeName) {
            HomePage(navHostController = navHostController)
        }

        composable(Route.CalendarPage.routeName) {
            CalendarPage(navHostController = navHostController)
        }

        composable(Route.SettingPage.routeName) {
            SettingPage(navHostController = navHostController)
        }

        composable(Route.AlertPage.routeName) {
            AlertPage(navHostController = navHostController)
        }

    }

}