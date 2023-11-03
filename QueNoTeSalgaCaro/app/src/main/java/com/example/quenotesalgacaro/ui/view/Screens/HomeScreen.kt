package com.example.quenotesalgacaro.ui.view.Screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.NavigationState
import com.example.quenotesalgacaro.navigation.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopBar(title = "Home", navController = navController)
        },
        bottomBar = {
            // ButtomBar()
        }
    ) {

    }
}