package com.espoch.comedor.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface PayPhoneApiService {
    @POST("payment")
    fun makePayment(@Body request: PaymentRequest?): Call<PaymentResponse?>?
}

class PaymentResponse {
    @SerializedName("transactionId")
    var transactionId: String? = null

    @SerializedName("status")
    var status: String? = null

    // Puedes agregar más propiedades según la respuesta esperada de la API

    override fun toString(): String {
        return "PaymentResponse(transactionId=$transactionId, status=$status)"
    }
}

class PaymentRequest(
    val amount: Double,
    val currency: String,
    val description: String
)
