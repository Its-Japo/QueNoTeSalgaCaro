package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class Transaction(
    @PropertyName("id") var id: String = "",
    @PropertyName("amount") val amount: Double = 0.0,
    @PropertyName("category") val category: String = "",
    @PropertyName("date") val date: String = "",
    @PropertyName("day") val day: Int = 0,
    @PropertyName("description") val description: String = "",
)
