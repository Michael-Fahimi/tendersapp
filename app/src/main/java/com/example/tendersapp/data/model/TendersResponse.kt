package com.example.tendersapp.data.model


import com.google.gson.annotations.SerializedName

data class TendersResponse(
    @SerializedName("data") val tenders: List<Tender>,
    @SerializedName("meta") val meta: Meta
)

data class Tender(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("organization") val organization: String?,
    @SerializedName("value") val value: Double?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("publication_date") val publicationDate: String?,
    @SerializedName("deadline") val deadline: String?
)

data class Meta(
    @SerializedName("total") val total: Int?,
    @SerializedName("per_page") val perPage: Int?,
    @SerializedName("current_page") val currentPage: Int?,
    @SerializedName("last_page") val lastPage: Int?
)
