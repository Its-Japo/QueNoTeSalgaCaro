package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class Wallet(
    @PropertyName("name") val name: String = ""
)
