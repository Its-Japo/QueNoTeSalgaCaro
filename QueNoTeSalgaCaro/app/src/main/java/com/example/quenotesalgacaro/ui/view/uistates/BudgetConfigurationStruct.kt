package com.example.quenotesalgacaro.ui.view.uistates

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration

data class BudgetConfigurationStruct(
    val income: List<BudgetConfiguration> = emptyList(),
    val fixedExpenses: List<BudgetConfiguration> = emptyList(),
    val variableExpenses: List<BudgetConfiguration> = emptyList()
)
