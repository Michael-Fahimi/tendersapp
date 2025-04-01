package com.example.tendersapp.presentation.tenders


import com.example.tendersapp.domain.model.TenderDomain

sealed class TendersState {
    object Idle : TendersState()
    object Loading : TendersState()
    data class Success(
        val tenders: List<TenderDomain>,
        val currentPage: Int = 1,
        val isLastPage: Boolean = false
    ) : TendersState()
    data class Error(val message: String) : TendersState()
}