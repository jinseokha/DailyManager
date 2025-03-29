package com.devseok.dailymanager.navigation

sealed class Route(
    val routeName: String
) {
    object HomePage: Route(routeName = "home_page")

    object CalendarPage: Route(routeName = "calendar_page")

    object AlertPage: Route(routeName = "alert_page")

    object SettingPage: Route(routeName = "setting_page")

}