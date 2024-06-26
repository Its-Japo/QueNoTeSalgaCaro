package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WalletConfigurationScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    navController: NavController,
    walletName: String?
) {

    val walletConfig by walletViewModel.fetchWalletCategoriesState.collectAsState()
    val uid = authViewModel.loginUiState.value.user?.uid

    LaunchedEffect(
        key1 = uid,
        block = {
            uid?.let {
                if (walletName != null) {
                    walletViewModel.fetchWalletCategories(uid, walletName)
                }
            }
        }
    )

    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.ConfigureWallet), navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("AddWalletCategoryScreen/$walletName")
                },
            ) {
                Text(text = "+", fontSize = 30.sp)
            }
        }
    ) {
        paddingValues ->
        when(val state = walletConfig) {
            is DataUiState.Success -> {

                LazyColumn(contentPadding = paddingValues){
                    item {
                        Text(
                            text = stringResource(id = R.string.Categories),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(10.dp, 5.dp, 10.dp, 5.dp),
                            fontSize = 20.sp,
                            color = Color.LightGray
                        )
                        Spacer(modifier = modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp, 20.dp, 0.dp)
                            .background(Color.Gray)
                        )
                    }

                    items(
                        state.data.size
                    ) {subIndex ->
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {


                            Text(
                                text = (state.data[subIndex].name),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .weight(4f)
                                    .padding(30.dp, 5.dp, 10.dp, 5.dp),
                            )

                            IconButton(
                                onClick = {
                                    if (walletName != null) {
                                        walletViewModel.deleteWalletCategory(uid, walletName, state.data[subIndex].id)
                                    }
                                },
                                modifier = modifier
                                    .weight(1f)
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                                Icon (
                                    painter = painterResource(id = R.drawable.outline_delete_24),
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = modifier
                                        .scale(1.3f)
                                )
                            }

                        }
                    }
                }
            }
            is DataUiState.Error -> {
                ErrorScreen(error = state.exception, paddingValues = paddingValues)
            }
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
            }
        }

    }
}



