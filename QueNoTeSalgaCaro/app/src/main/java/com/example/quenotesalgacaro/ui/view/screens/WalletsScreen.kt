package com.example.quenotesalgacaro.ui.view.screens

import WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel()
) {
    val walletsFetchState by walletViewModel.walletsFetchState.collectAsState()
    val user = authViewModel.loginUiState.value.user


    LaunchedEffect(user) {
        user?.let {
            walletViewModel.fetchWallets(it.uid)
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = "Wallets", navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("CreateScreen/wallet")
                },
            ) {
                Text(text = "+", fontSize = 30.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        when (val state = walletsFetchState) {
            is DataUiState.Loading -> CircularProgressIndicator()
            is DataUiState.Success -> {
                LazyColumn(contentPadding = paddingValues) {
                    items(state.data) { wallet ->
                        InfoBar(text = wallet.name, onClick = { /*TODO*/ })
                    }
                }
            }
            is DataUiState.Error -> Text("Error: ${state.exception.message}")
            else -> Text("Something went wrong")
        }
    }
}
