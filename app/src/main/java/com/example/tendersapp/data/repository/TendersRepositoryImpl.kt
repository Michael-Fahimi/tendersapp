package com.example.tendersapp.data.repository


import com.example.tendersapp.domain.model.Result
import com.example.tendersapp.domain.model.TenderDomain
import com.example.tendersapp.data.remote.TendersApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TendersRepositoryImpl @Inject constructor(
    private val apiService: TendersApiService
) : TendersRepository {

    override fun getTenders(page: Int, limit: Int): Flow<Result<List<TenderDomain>>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getTenders(page, limit)
            val tenders = response.tenders.map { tender ->
                TenderDomain(
                    id = tender.id.toString(),
                    title = tender.title.toString(),
                    organization = tender.organization.toString(),
                    value = tender.value,
                    currency = tender.currency,
                    publicationDate = tender.publicationDate.toString(),
                    deadline = tender.deadline
                )
            }
            emit(Result.Success(tenders))
        } catch (e: HttpException) {
            emit(Result.Error(message = "HTTP Error: ${e.message}", code = e.code()))
        } catch (e: IOException) {
            emit(Result.Error(message = "Network Error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error(message = "Unknown Error: ${e.message}"))
        }
    }
}