package com.example.quenotesalgacaro.ui.view.vms

import androidx.lifecycle.ViewModel
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository

class BudgetViewModel(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
): ViewModel() {

}