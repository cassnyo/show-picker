package com.cassnyo.showpicker.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedResponse<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)