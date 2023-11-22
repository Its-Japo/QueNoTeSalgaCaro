package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class FundTransaction(
    @PropertyName("amount") val amount: Double = 0.0,
    @PropertyName("date") val date: String = "",
    @PropertyName("type") val type: String = "",
    @PropertyName("month") val month: String = "",
    @PropertyName("year") val year: Int = 0,
    @PropertyName("day") val day: Int = 0,
    @PropertyName("id") var id: String = ""
)
