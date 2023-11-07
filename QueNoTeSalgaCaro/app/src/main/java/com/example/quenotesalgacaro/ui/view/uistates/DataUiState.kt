package com.example.quenotesalgacaro.ui.view.uistates

sealed class DataUiState<out T> {
    object Loading : DataUiState<Nothing>()
    data class Success<T>(val data: T) : DataUiState<T>()
    data class Error(val exception: Throwable) : DataUiState<Nothing>()
}