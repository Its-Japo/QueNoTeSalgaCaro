package com.example.quenotesalgacaro.ui.view.uistates

import com.google.firebase.auth.FirebaseUser

data class RegisterUiState(
    val loading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val success: Boolean = false
)