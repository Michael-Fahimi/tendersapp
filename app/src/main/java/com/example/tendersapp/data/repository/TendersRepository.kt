package com.example.tendersapp.data.repository


import com.example.tendersapp.domain.model.Result
import com.example.tendersapp.domain.model.TenderDomain
import kotlinx.coroutines.flow.Flow

interface TendersRepository {
    fun getTenders(page: Int, limit: Int): Flow<Result<List<TenderDomain>>>
}