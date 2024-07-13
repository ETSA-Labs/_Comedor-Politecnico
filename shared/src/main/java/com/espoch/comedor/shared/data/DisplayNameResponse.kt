package com.espoch.comedor.shared.data

import com.google.gson.annotations.SerializedName

data class DisplayNameResponse(
    @SerializedName("displayName")
    val value: String
)