package com.espoch.comedor.shared.services

import com.espoch.comedor.shared.data.DisplayNameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * GraphService is an interface for making API calls to Microsoft Graph.
 * It provides a method to get the display name of the authenticated user.
 */
interface GraphService {

    /**
     * Gets the display name of the authenticated user.
     *
     * @param accessToken The access token for authorization.
     * @return A Call object which can be used to request the user's display name.
     */
    @GET("/v1.0/me")
    fun getDisplayName(
        @Header("Authorization") accessToken: String
    ): Call<DisplayNameResponse>
}
