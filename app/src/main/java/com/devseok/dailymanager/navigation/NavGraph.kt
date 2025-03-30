package com.devseok.dailymanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devseok.dailymanager.feature.home.HomePage
import com.devseok.dailymanager.feature.home.HomePageVM

@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Route.HomePage.routeName
    ) {
        composable(Route.HomePage.routeName) {
            HomePage(navHostController = navHostController)
        }

    }

}