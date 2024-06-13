package com.espoch.comedor.services

import com.espoch.comedor.data.UserDisplayName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GraphService {
    @GET("/v1.0/me")
    fun getUserInfo(@Header("Authorization") accessToken: String): Call<UserDisplayName>
}