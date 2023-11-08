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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
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
            TopBar(title = "Ajustes", navController = navController)
        }
    ) {
       Column (
           modifier = modifier.padding(0.dp, 60.dp, 0.dp, 0.dp)
       ){
           ButtonBar(icon =  R.drawable.profileicon, text = "Account", onClick = { navController.navigate("AccountScreen") })
           ButtonBar(icon =  R.drawable.wallet_icon, text = "Wallets", onClick = { navController.navigate("WalletsScreen") })
           ButtonBar(icon =  R.drawable.check_icon, text = "Budgets", onClick = { navController.navigate("BudgetsScreen") })
           ButtonBar(icon =  R.drawable.piggy_icon, text = "Funds", onClick = { navController.navigate("FundsScreen") })
           Spacer(modifier = modifier.height(60.dp))
           Spacer(modifier = modifier
               .height(1.dp)
               .padding(20.dp, 0.dp, 20.dp, 0.dp)
               .background(Color.LightGray)
               .fillMaxWidth()
           )
           ButtonBar(icon =  R.drawable.question_icon, text = "Help & Feedback", onClick = { /*TODO*/ })
           ButtonBar(icon =  R.drawable.info_icon, text = "About", onClick = { /*TODO*/ })
       }
    }

}



@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = NavController(LocalContext.current))
}