package com.example.quenotesalgacaro.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.quenotesalgacaro.R

sealed class NavigationBarState (
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object HomeScreen: NavigationBarState(
        route = "home",
        title = "Home",
        icon = R.drawable.home_icon
    )
    object StatsInfoScreen: NavigationBarState(
        route = "statsinfo",
        title = "Stats",
        icon = R.drawable.wallet_icon
    )
    object AddTransactionScreen: NavigationBarState(
        route = "addtransaction",
        title = "Add Transaction",
        icon = R.drawable.add_icon
    )
    object FundsInfoScreen: NavigationBarState(
        route = "fundsinfo",
        title = "Funds",
        icon = R.drawable.pie_graph_icon
    )
    object BudgetInfoScreen: NavigationBarState(
        route = "budgetinfo",
        title = "Budgets",
        icon = R.drawable.piggy_icon
    )

}
