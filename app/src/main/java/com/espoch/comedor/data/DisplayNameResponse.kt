package com.espoch.comedor.data

import com.google.gson.annotations.SerializedName

data class DisplayNameResponse(
    @SerializedName("displayName")
    val value: String
)