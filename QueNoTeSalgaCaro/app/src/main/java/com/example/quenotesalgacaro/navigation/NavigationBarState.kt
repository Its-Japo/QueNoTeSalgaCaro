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
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int = icon
) {
    object HomeScreen: NavigationBarState(
        route = "home",
        title = "Home",
        icon = R.drawable.home_icon,
        iconSelected = R.drawable.home_icon_filled
    )
    object StatsInfoScreen: NavigationBarState(
        route = "statsinfo",
        title = "Stats",
        icon = R.drawable.pie_graph_icon,
        iconSelected = R.drawable.pie_graph_icon_filled
    )
    object AddTransactionScreen: NavigationBarState(
        route = "addtransaction",
        title = "Add Transaction",
        icon = R.drawable.add_icon,
        iconSelected = R.drawable.add_icon
    )
    object FundsInfoScreen: NavigationBarState(
        route = "fundsinfo",
        title = "Funds",
        icon = R.drawable.piggy_icon,
        iconSelected = R.drawable.piggy_icon_filled
    )
    object BudgetInfoScreen: NavigationBarState(
        route = "budgetinfo",
        title = "Budgets",
        icon = R.drawable.wallet_icon,
        iconSelected = R.drawable.wallet_icon_filled
    )

}
