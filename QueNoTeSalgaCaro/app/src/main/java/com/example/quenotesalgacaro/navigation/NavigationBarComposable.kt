package com.example.quenotesalgacaro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarState.HomeScreen.route
    ) {

        composable(NavigationBarState.HomeScreen.route) {
            HomeScreen(viewModel = authViewModel)
        }
        composable(NavigationBarState.StatsInfoScreen.route) {
            StatsInfoScreen()
        }
        composable(NavigationBarState.AddTransactionScreen.route) {
            AddTransactionScreen(viewModel = authViewModel)
        }
        composable(NavigationBarState.BudgetInfoScreen.route) {
            BudgetInfoScreen()
        }
        composable(NavigationBarState.FundsInfoScreen.route) {
            FundsInfoScreen()
        }

    }
}