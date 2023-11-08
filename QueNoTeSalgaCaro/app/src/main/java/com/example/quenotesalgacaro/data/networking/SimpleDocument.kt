package com.example.quenotesalgacaro.data.networking

import com.google.firebase.firestore.PropertyName

data class SimpleDocument(
    @PropertyName("name") val name: String = ""
)
