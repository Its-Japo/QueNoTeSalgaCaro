package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.ButtonBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Scaffold (
        topBar = {
            TopBar(title = "Settings", navController = navController)
        }
    ) {
       Column (
           modifier = modifier.padding(0.dp, 60.dp, 0.dp, 0.dp)
       ){
           ButtonBar(text = "Account", onClick = { navController.navigate("AccountScreen") })
           ButtonBar(text = "Wallets", onClick = { /*TODO*/ })
           ButtonBar(text = "Budgets", onClick = { /*TODO*/ })
           ButtonBar(text = "Funds", onClick = { /*TODO*/ })
           Spacer(modifier = modifier.height(60.dp))
           Spacer(modifier = modifier.height(1.dp)
               .padding(20.dp, 0.dp, 20.dp, 0.dp)
               .background(Color.LightGray)
               .fillMaxWidth()
           )
           ButtonBar(text = "Help & Feedback", onClick = { /*TODO*/ })
           ButtonBar(text = "About", onClick = { /*TODO*/ })
       }
    }

}



@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = NavController(LocalContext.current))
}