package com.example.tendersapp.domain.model


data class TenderDomain(
    val id: String,
    val title: String,
    val organization: String,
    val value: Double?,
    val currency: String?,
    val publicationDate: String,
    val deadline: String?
)

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Success<T>(data: T) : Result<T>(data = data)
    class Error<T>(message: String, code: Int? = null, data: T? = null) : Result<T>(data = data, message = message, code = code)
    class Loading<T>(data: T? = null) : Result<T>(data = data)
}