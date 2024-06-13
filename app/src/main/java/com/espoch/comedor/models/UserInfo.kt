package com.espoch.comedor.models

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id")
    val id: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("description")
    val description: String
)