package com.cassnyo.showpicker.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, "TODO")
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

}