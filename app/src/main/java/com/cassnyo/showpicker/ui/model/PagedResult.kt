package com.cassnyo.showpicker.ui.model

data class PagedResult<T>(
    val data: List<T>,
    val page: Int,
    val totalPages: Int
)