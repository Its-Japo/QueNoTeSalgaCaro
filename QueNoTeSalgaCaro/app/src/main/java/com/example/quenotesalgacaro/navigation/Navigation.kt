package com.example.quenotesalgacaro.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.ui.view.Screens.HomeScreen
import com.example.quenotesalgacaro.ui.view.Screens.LoginScreen
import com.example.quenotesalgacaro.ui.view.Screens.RegisterScreen
import com.example.quenotesalgacaro.ui.view.Screens.SettingsScreen
import com.example.quenotesalgacaro.ui.view.VMs.AuthViewModel


@Composable
fun Navigation(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
)
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationState.LoginScreen.route,
        modifier = modifier
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

    }
}