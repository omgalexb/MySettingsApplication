package com.lexxapps.mysettings.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lexxapps.mysettings.ui.screens.main.MainDestination
import com.lexxapps.mysettings.ui.screens.main.MainScreen
import com.lexxapps.mysettings.ui.screens.settings.SettingsDestination
import com.lexxapps.mysettings.ui.screens.settings.SettingsScreen

@Composable
fun MySettingsNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = MainDestination.route) {
        composable(MainDestination.route) {
            MainScreen(
                onOpenSettings = { navController.navigate(SettingsDestination.route) }
            )
        }
        composable(SettingsDestination.route) {
            SettingsScreen(
                onNavBack = { navController.popBackStack() }
            )
        }
    }
}