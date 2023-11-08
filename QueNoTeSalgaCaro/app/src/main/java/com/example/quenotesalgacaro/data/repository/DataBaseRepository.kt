package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.Wallet
import com.google.firebase.auth.FirebaseUser

interface DataBaseRepository {

    suspend fun registerNewUser(user: FirebaseUser?, password: String)
    suspend fun deleteUser(user: FirebaseUser?)
    suspend fun getSubcollection(uid: String, collectionName: String): Result<List<Wallet>>
    suspend fun addFirstGradeSubcollection(uid: String?, name: String, collectionName: String): Result<Unit>
}