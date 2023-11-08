package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.google.firebase.auth.FirebaseUser

interface DataBaseRepository {

    suspend fun registerNewUser(user: FirebaseUser?, password: String)
    suspend fun deleteUser(user: FirebaseUser?)
    suspend fun getFirstGradeSubcollection(uid: String, collectionName: String): Result<List<SimpleDocument>>
    suspend fun addFirstGradeSubcollection(uid: String?, name: String, collectionName: String): Result<Unit>

    suspend fun getSecondGradeSubcollection(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<BudgetConfiguration>>
}