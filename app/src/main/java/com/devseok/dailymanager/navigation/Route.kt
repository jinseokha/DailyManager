package com.devseok.dailymanager.navigation

import androidx.annotation.DrawableRes

sealed class Route(
    val routeName: String,
    @DrawableRes val icon: Int?
) {
    // TODO : 아이콘 추가해야함 (Home, Calendar, Setting)
    object HomePage: Route(routeName = "home_page", icon = null)

    object CalendarPage: Route(routeName = "calendar_page", icon = null)

    object AlertPage: Route(routeName = "alert_page", icon = null)

    object SettingPage: Route(routeName = "setting_page", icon = null)

}