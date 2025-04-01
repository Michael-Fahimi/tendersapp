package com.example.tendersapp.data.remote


import com.example.tendersapp.data.model.TendersResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface TendersApiService {
    @GET("tenders")
    suspend fun getTenders(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): TendersResponse
}
