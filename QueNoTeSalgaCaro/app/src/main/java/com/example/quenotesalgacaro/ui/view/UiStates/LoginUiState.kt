package com.example.quenotesalgacaro.ui.view.UiStates

import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val loading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val success: Boolean = false
)
