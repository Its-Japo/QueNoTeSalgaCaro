package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.navigation.NavigationBarComposable
import com.example.quenotesalgacaro.navigation.NavigationHost
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun NavigationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),

) {
    val navHostController = rememberNavController()
    Scaffold (
        bottomBar = {
            NavigationHost(navController = navHostController)
        },
        topBar = { TopBar(title = "QUE NO TE SALGA CARO", navController = navController, auth = authViewModel.loginUiState.value.user != null) }

    ) {
        NavigationBarComposable(
            navController = navHostController,
            authViewModel = authViewModel,
            paddingValues = it
        )
    }
}