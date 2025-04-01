package com.example.tendersapp.domain.usecase


import com.example.tendersapp.data.repository.TendersRepository
import com.example.tendersapp.domain.model.Result
import com.example.tendersapp.domain.model.TenderDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTendersUseCase @Inject constructor(
    private val repository: TendersRepository
) {
    operator fun invoke(page: Int = 1, limit: Int = 2): Flow<Result<List<TenderDomain>>> {
        return repository.getTenders(page, limit)
    }
}