package com.example.quenotesalgacaro.navigation


import BudgetViewModel
import FundViewModel
import WalletViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.ui.view.screens.AccountScreen
import com.example.quenotesalgacaro.ui.view.screens.AddBudgetRowScreen
import com.example.quenotesalgacaro.ui.view.screens.AddWalletCategoryRowScreen
import com.example.quenotesalgacaro.ui.view.screens.BudgetConfigurationScreen
import com.example.quenotesalgacaro.ui.view.screens.BudgetsScreen
import com.example.quenotesalgacaro.ui.view.screens.CreateScreen
import com.example.quenotesalgacaro.ui.view.screens.FundsScreen
import com.example.quenotesalgacaro.ui.view.screens.LoginScreen
import com.example.quenotesalgacaro.ui.view.screens.NavigationScreen
import com.example.quenotesalgacaro.ui.view.screens.RegisterScreen
import com.example.quenotesalgacaro.ui.view.screens.SettingsScreen
import com.example.quenotesalgacaro.ui.view.screens.WalletConfigurationScreen
import com.example.quenotesalgacaro.ui.view.screens.WalletsScreen
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel


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
            NavigationState.NavigationScreen.route
        } else {
            NavigationState.LoginScreen.route
        }
    ) {
        composable(NavigationState.LoginScreen.route) {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        composable(NavigationState.NavigationScreen.route) {
            NavigationScreen(navController = navController, authViewModel = authViewModel)
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
            FundsScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationState.WalletsScreen.route) {
            WalletsScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationState.BudgetsScreen.route) {
            BudgetsScreen(navController = navController, authViewModel = authViewModel)
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
                    actionViewModel = FundViewModel()
                )
            }
        }
        }
        composable(NavigationState.BudgetConfigurationScreen.route + "/{id}") { backstackEntry -> run {
            val id = backstackEntry.arguments?.getString("id")
            BudgetConfigurationScreen(
                navController = navController,
                authViewModel = authViewModel,
                id = id!!
            )
        }
        }
        composable(NavigationState.AddBudgetRowScreen.route + "/{operation}/{id}") { backstackEntry -> run {
            val operation = backstackEntry.arguments?.getString("operation")
            val id = backstackEntry.arguments?.getString("id")
            AddBudgetRowScreen(
                navController = navController,
                authViewModel = authViewModel,
                operation = operation!!,
                budgetName = id!!
            )
        }
        }
        composable(NavigationState.WalletConfigurationScreen.route + "/{walletname}") {navBackStackEntry -> run{
            val walletName = navBackStackEntry.arguments?.getString("walletname")
            WalletConfigurationScreen(
                navController = navController,
                authViewModel = authViewModel,
                walletName = walletName!!
            )
        }
        }
        composable(NavigationState.AddWalletCategoryScreen.route + "/{walletName}") { navBackStackEntry ->
            run {
                val walletName = navBackStackEntry.arguments?.getString("walletName")
                AddWalletCategoryRowScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    walletName = walletName!!
                )
            }
        }
    }
}

