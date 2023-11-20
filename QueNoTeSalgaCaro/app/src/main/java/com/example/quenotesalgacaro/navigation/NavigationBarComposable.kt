package com.example.quenotesalgacaro.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quenotesalgacaro.ui.view.screens.AddTransactionScreen
import com.example.quenotesalgacaro.ui.view.screens.BudgetInfoScreen
import com.example.quenotesalgacaro.ui.view.screens.FundsInfoScreen
import com.example.quenotesalgacaro.ui.view.screens.HomeScreen
import com.example.quenotesalgacaro.ui.view.screens.StatsInfoScreen
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@Composable
fun NavigationBarComposable(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarState.HomeScreen.route
    ) {

        composable(NavigationBarState.HomeScreen.route) {
            HomeScreen(viewModel = authViewModel, paddingValues = paddingValues)
        }
        composable(NavigationBarState.StatsInfoScreen.route) {
            StatsInfoScreen(paddingValues = paddingValues)
        }
        composable(NavigationBarState.AddTransactionScreen.route) {
            AddTransactionScreen(navController = navController, viewModel = authViewModel, paddingValues = paddingValues)
        }
        composable(NavigationBarState.BudgetInfoScreen.route) {
            BudgetInfoScreen(paddingValues = paddingValues)
        }
        composable(NavigationBarState.FundsInfoScreen.route) {
            FundsInfoScreen(paddingValues = paddingValues)
        }

    }
}