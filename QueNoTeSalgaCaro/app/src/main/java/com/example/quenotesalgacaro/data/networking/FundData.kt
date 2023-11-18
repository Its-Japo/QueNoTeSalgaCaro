package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class FundData(
    @PropertyName("id") val id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("initialcapital") val initialCapital: Double = 0.0,
    @PropertyName("interest") val interest: Double = 0.0,
    @PropertyName("anualcapitalizations") val anualcapitalizations: Int = 0,
    @PropertyName("goal") val goal: Double = 0.0,
)
