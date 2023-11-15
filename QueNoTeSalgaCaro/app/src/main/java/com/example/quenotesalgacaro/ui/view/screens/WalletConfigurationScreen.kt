package com.example.quenotesalgacaro.ui.view.screens

import WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletConfigurationScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    navController: NavController,
    walletName: String?
) {

    val walletConfig by walletViewModel.addWalletCategoryState.collectAsState()
    val uid = authViewModel.loginUiState.value.user?.uid

    LaunchedEffect(
        key1 = uid,
        block = {
            uid?.let {
                walletViewModel.fetchWallets(it)
            }
        }
    )

    Scaffold (
        topBar = {
            TopBar(title = "Configure Wallet", navController = navController)
        }
    ) {
        paddingValues ->
        when(val state = walletConfig) {
            is DataUiState.Success -> {
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(text = "Wallet Configured")
                }
            }
            is DataUiState.Error -> {
                Text(text = "Error")
            }
            is DataUiState.Loading -> {
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp, 10.dp, 10.dp, 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .scale(1.3f)
                    )
                }
            }
        }

    }
}



