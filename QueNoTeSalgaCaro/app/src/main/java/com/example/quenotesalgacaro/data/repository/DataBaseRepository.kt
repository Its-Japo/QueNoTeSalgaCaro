package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.google.firebase.auth.FirebaseUser

interface DataBaseRepository {

    suspend fun registerNewUser(user: FirebaseUser?, password: String)
    suspend fun deleteUser(user: FirebaseUser?)
    suspend fun getFirstGradeSubcollection(uid: String, collectionName: String): Result<List<SimpleDocument>>
    suspend fun addFirstGradeSubcollection(uid: String?, name: String, collectionName: String): Result<Unit>
    suspend fun getSecondGradeSubcollectionBudget(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<BudgetConfiguration>>
    suspend fun getSecondGradeSubcollectionWallet(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<SimpleDocument>>
    suspend fun deleteSecondGradeSubcollectionDocument(uid: String?, collectionName: String, entity: String, subcollectionName: String, documentName: String): Result<Unit>
    suspend fun addSecondGradeSubcollectionDocumentBudget(uid: String?, collectionName: String, entity: String, subcollectionName: String, budgetConfiguration: BudgetConfiguration): Result<Unit>
    suspend fun addSecondGradeSubcollectionDocumentWallet(uid: String?, collectionName: String, entity: String, subcollectionName: String, data: SimpleDocument): Result<Unit>
    suspend fun updateDocument(uid: String?, collectionName: String, documentId: String, fieldName: String, element: Any): Result<Unit>
}