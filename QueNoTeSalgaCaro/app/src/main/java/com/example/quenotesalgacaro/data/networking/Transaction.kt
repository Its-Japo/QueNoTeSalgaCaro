package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class Transaction(
    @PropertyName("amount") val amount: Double,
    @PropertyName("category") val category: String,
    @PropertyName("date") val date: String,
    @PropertyName("day") val day: Int,
    @PropertyName("description") val description: String,
)
