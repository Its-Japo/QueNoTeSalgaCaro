package com.example.quenotesalgacaro.navigation

sealed class NavigationState(val route: String) {


    object NavigationScreen: NavigationState("NavigationScreen")
    object LoginScreen: NavigationState("LoginScreen")
    object RegisterScreen: NavigationState("RegisterScreen")
    object SettingsScreen: NavigationState("SettingsScreen")
    object AccountScreen: NavigationState("AccountScreen")
    object FundsScreen: NavigationState("FundsScreen")
    object BudgetsScreen: NavigationState("BudgetsScreen")
    object WalletsScreen: NavigationState("WalletsScreen")
    object CreateScreen: NavigationState("CreateScreen")
    object BudgetConfigurationScreen: NavigationState("BudgetConfigurationScreen")
    object AddBudgetRowScreen: NavigationState("AddBudgetRowScreen")
}
