package com.devseok.dailymanager.navigation

sealed class Screen(val route: String, val title: String) {

    object Calender: Screen(Route.CalendarPage.routeName, Route.CalendarPage.routeName)
    object Alert: Screen(Route.AlertPage.routeName, Route.AlertPage.routeName)
    object Settings: Screen(Route.SettingPage.routeName, Route.SettingPage.routeName)
    object Login: Screen(Route.LoginPage.routeName, Route.LoginPage.routeName)
    object Splash: Screen(Route.SplashPage.routeName, Route.SplashPage.routeName)
}