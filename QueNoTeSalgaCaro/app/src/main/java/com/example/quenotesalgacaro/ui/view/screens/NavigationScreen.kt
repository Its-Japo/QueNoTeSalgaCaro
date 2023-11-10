package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quenotesalgacaro.navigation.NavigationBarComposable
import com.example.quenotesalgacaro.navigation.NavigationHost
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val navHostController = rememberNavController()
    Scaffold (
        bottomBar = {
            NavigationHost(navController = navHostController)
        },
        topBar = { TopBar(title = "QUE NO TE SALGA CARO", navController = navController, auth = authViewModel.loginUiState.value.user != null) }

    ) {
        NavigationBarComposable(
            navController = navHostController
        )
    }
}