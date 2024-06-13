package com.espoch.comedor.data

import com.google.gson.annotations.SerializedName

data class UserDisplayName(
    @SerializedName("displayName")
    val value: String
)