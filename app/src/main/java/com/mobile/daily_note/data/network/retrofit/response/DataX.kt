package com.mobile.daily_note.data.network.retrofit.response


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)