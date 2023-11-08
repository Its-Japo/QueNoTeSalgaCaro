package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class BudgetConfiguration(
    @PropertyName("amount") val amount: String = "",
    @PropertyName("concept") val concept: String = "",
)
