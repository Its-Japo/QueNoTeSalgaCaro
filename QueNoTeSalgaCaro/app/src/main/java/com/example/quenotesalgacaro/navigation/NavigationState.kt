package com.example.quenotesalgacaro.navigation

sealed class NavigationState(val route: String) {

    object LoginScreen: NavigationState("LoginScreen")
    object RegisterScreen: NavigationState("RegisterScreen")
    object HomeScreen: NavigationState("HomeScreen")
    object SettingsScreen: NavigationState("SettingsScreen")
    object AccountScreen: NavigationState("AccountScreen")
    object FundsScreen: NavigationState("FundsScreen")
    object WalletsScreen: NavigationState("WalletsScreen")
    object BudgetsScreen: NavigationState("BudgetsScreen")
    object AddTransactionScreen: NavigationState("AddTransactionScreen")
}
