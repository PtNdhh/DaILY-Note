package com.mobile.daily_note.data.network.retrofit.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accessToken")
    val accessToken: String
)