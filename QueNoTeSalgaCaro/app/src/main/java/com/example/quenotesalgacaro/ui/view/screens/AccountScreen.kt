package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AccountScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
){

    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.Account), navController = navController)
        }
    ){
        Column(
            modifier = modifier.padding(it)
        ){
            Row (
                modifier = modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = stringResource(id = R.string.Account),
                    modifier = modifier
                        .padding(20.dp)
                        .scale(4f)
                        .weight(2f)
                )
                Text(text = "${stringResource(id = R.string.mail)} ${viewModel.loginUiState.value.user?.email}",
                    modifier = modifier.weight(3f)

                )
            }
            Row {
                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate("LoginScreen") {
                            popUpTo("NavigationScreen") { inclusive = true }
                        }
                    },
                    modifier = if (viewModel.loginUiState.value.user != null) {
                        modifier
                            .weight(1f)
                            .padding(20.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Red,
                                shape = MaterialTheme.shapes.extraLarge
                            )} else {
                        modifier
                            .weight(1f)
                            .padding(20.dp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = Color.Red
                    ),
                    enabled = viewModel.loginUiState.value.user != null
                ) {
                    Text(text = stringResource(id = R.string.Logout))
                }
                Button(
                    onClick = {
                        viewModel.deleteUser()
                        navController.navigate("LoginScreen") {
                            popUpTo("NavigationScreen") { inclusive = true }
                        }
                    },
                    modifier = modifier
                        .weight(1f)
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    enabled = viewModel.loginUiState.value.user != null
                ) {
                    Text(text = stringResource(id = R.string.DeleteAccount))
                }
            }
        }

    }
}
