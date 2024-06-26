package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.navigation.NavigationBarComposable
import com.example.quenotesalgacaro.navigation.NavigationHost
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R

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
        topBar = { TopBar(title = stringResource(id = R.string.app_name), navController = navController, auth = authViewModel.loginUiState.value.user != null) }

    ) {
        NavigationBarComposable(
            navHostController = navHostController,
            navController = navController,
            authViewModel = authViewModel,
            paddingValues = it
        )
    }
}