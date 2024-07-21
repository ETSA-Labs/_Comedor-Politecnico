package com.espoch.comedor.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL = "https://api.brevo.com/"

    val brevoApiService: BrevoApiService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        brevoApiService = retrofit.create(BrevoApiService::class.java)
    }
}
