package com.devseok.dailymanager.feature.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.devseok.dailymanager.navigation.Route

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        Route.HomePage,
        Route.CalendarPage,
        Route.SettingPage
    )

    AnimatedVisibility(
        visible = items.map { it.routeName }.contains(currentRoute)
    ) {
        NavigationBar(
            modifier = modifier
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.routeName,
                    onClick = {
                        navHostController.navigate(item.routeName) {
                            navHostController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (item.icon != null) {
                            Icon(
                                painter = painterResource(id = item.icon!!),
                                contentDescription = item.routeName
                            )
                        }
                    }
                )
            }

        }

    }




}