package com.cassnyo.showpicker.data.mapper

import com.cassnyo.showpicker.data.network.model.PagedResponse
import com.cassnyo.showpicker.ui.model.PagedResult

fun <T, P> PagedResponse<T>.mapToPagedResult(transform: (T) -> P): PagedResult<P> {
    return PagedResult(
        results.map { transform(it) },
        page,
        totalPages
    )
}