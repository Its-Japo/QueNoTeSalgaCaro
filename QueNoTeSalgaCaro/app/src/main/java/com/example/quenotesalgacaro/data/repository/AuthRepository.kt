package com.example.quenotesalgacaro.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun signIn(
        email: String,
        password: String
    ) : FirebaseUser?

    suspend fun registerNewUser(
        email: String,
        password: String
    ) : FirebaseUser?

    suspend fun signOut()

    suspend fun deleteUser()

    fun getCurrentUser(): FirebaseUser?

}