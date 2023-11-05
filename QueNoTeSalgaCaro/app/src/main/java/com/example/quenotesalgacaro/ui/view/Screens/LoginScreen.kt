package com.example.quenotesalgacaro.ui.view.Screens


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.ui.view.VMs.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val usernameState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginUiState.collectLatest { loginUiState ->
            if (loginUiState.user != null) {
                Toast.makeText(context, "Usuario logueado", Toast.LENGTH_SHORT).show()
                navController.navigate("HomeScreen") {
                    popUpTo("LoginScreen") { inclusive = true }
                }
            } else if (loginUiState.error != null) {
                Toast.makeText(context, "Error al loguear usuario: ${loginUiState.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = "Login", navController = navController)
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
                    text = "¿No tienes cuenta? Regístrate",
                )
            }
        }
    ){
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 60.dp, 10.dp, 10.dp),
        ) {
            Image (
                painter = painterResource(id = R.drawable.profileicon),
                contentDescription = "Logo",
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
                label = { Text("Correo electrónico") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                singleLine = true
            )
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Contraseña") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                singleLine = true
            )

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onClick = {
                viewModel.login(usernameState.value.text, passwordState.value.text)

            }) {
                Text("Login")
            }

        }
    }

}