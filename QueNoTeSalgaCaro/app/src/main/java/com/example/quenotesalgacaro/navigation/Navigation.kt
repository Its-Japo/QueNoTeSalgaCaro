package com.example.quenotesalgacaro.navigation


import WalletViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.ui.view.screens.AccountScreen
import com.example.quenotesalgacaro.ui.view.screens.BudgetsScreen
import com.example.quenotesalgacaro.ui.view.screens.FundsScreen
import com.example.quenotesalgacaro.ui.view.screens.HomeScreen
import com.example.quenotesalgacaro.ui.view.screens.LoginScreen
import com.example.quenotesalgacaro.ui.view.screens.RegisterScreen
import com.example.quenotesalgacaro.ui.view.screens.SettingsScreen
import com.example.quenotesalgacaro.ui.view.screens.WalletsScreen
import com.example.quenotesalgacaro.ui.view.screens.AddTransactionScreen
import com.example.quenotesalgacaro.ui.view.screens.CreateScreen
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel



@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    isUserLoggedIn: Boolean = authViewModel.loginUiState.value.user != null
)
{
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isUserLoggedIn) {
            NavigationState.HomeScreen.route
        } else {
            NavigationState.LoginScreen.route
        }
    ) {
        composable(NavigationState.LoginScreen.route) {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.RegisterScreen.route) {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(NavigationState.AccountScreen.route) {
            AccountScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.FundsScreen.route) {
            FundsScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.WalletsScreen.route) {
            WalletsScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationState.BudgetsScreen.route) {
            BudgetsScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.AddTransactionScreen.route) {
            AddTransactionScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.CreateScreen.route + "/{type}") { backstackEntry -> run {
            val type = backstackEntry.arguments?.getString("type")
            when (type) {
                "wallet" -> CreateScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    actionViewModel = WalletViewModel()
                )

                "budget" -> CreateScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    actionViewModel = BudgetViewModel()
                )

                "fund" -> CreateScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    actionViewModel = BudgetViewModel()
                )
            }
        }
        }

    }
}