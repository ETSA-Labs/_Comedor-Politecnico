package com.espoch.comedor.services

import com.espoch.comedor.models.EmailData
import com.espoch.comedor.models.EmailData.EmailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BrevoApiService {
    @Headers("Content-Type: application/json", "api-key: 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")
    @POST("v3/smtp/email")
    fun sendEmail(@Body emailData: EmailData): Call<EmailResponse>
}
