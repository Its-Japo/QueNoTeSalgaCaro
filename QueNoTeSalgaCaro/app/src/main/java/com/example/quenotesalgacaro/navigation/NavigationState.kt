package com.example.quenotesalgacaro.navigation

sealed class NavigationState(val route: String) {

    object LoginScreen: NavigationState("LoginScreen")
    object RegisterScreen: NavigationState("RegisterScreen")
    object HomeScreen: NavigationState("HomeScreen")

}