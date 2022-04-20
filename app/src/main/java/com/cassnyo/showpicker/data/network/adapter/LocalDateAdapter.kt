package com.cassnyo.showpicker.data.network.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter {

    @ToJson
    fun toJson(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @FromJson
    fun fromJson(localDate: String): LocalDate? {
        return when {
            localDate.isEmpty() -> null
            else -> LocalDate.parse(localDate, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

}