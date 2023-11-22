package com.example.quenotesalgacaro.ui.view.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val usernameState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    val showPassword = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loginUiState.collectLatest { loginUiState ->
            if (loginUiState.user != null) {
                navController.navigate("NavigationScreen") {
                    popUpTo("LoginScreen") { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.Login), navController = navController, auth = viewModel.loginUiState.value.user != null)
        },
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate("RegisterScreen") {
                        popUpTo("LoginScreen") { inclusive = true }
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.NoAccountRegisterNow),
                )
            }
        }
    ){ paddingValues ->
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            Image (
                painter = painterResource(id = R.drawable.profileicon),
                contentDescription = stringResource(id = R.string.ProfileIcon),
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            TextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.outline_person_24),
                        contentDescription = stringResource(id = R.string.email),
                        modifier = Modifier
                            .height(30.dp),
                    )
                },
            )
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                visualTransformation =  if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.outline_lock_24),
                        contentDescription = stringResource(id = R.string.password),
                        modifier = Modifier
                            .height(30.dp),
                    )
                },
                trailingIcon = {
                    if (showPassword.value) {
                        IconButton(
                            onClick = { showPassword.value = false },
                            content = {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_visibility_off_24),
                                    contentDescription = stringResource(id = R.string.password),
                                )
                            }
                        )
                    } else {
                        IconButton(
                            onClick = { showPassword.value = true },
                            content = {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_visibility_24),
                                    contentDescription = stringResource(id = R.string.password),
                                )
                            }
                        )
                    }
                }
            )

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onClick = {
                viewModel.login(usernameState.value.text, passwordState.value.text)

            }) {
                Text(text = stringResource(id = R.string.Login))
            }

        }
    }

}