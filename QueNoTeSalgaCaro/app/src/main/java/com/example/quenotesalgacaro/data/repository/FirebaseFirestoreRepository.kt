package com.example.quenotesalgacaro.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseFirestoreRepository {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun registerNewUser(user: FirebaseUser?, password: String) {
        return withContext(Dispatchers.IO) {
            try {
                val userMap = hashMapOf(
                    "email" to user?.email,
                    "password" to password
                )
                firebaseFirestore.collection("users").document(user?.uid ?: "")
                    .set(userMap)
                    .addOnSuccessListener {
                        println("User added successfully")
                    }
                    .addOnFailureListener {
                        println("Error adding user")
                    }
            } catch (e: Exception) {
                throw e
            }
        }
    }



}