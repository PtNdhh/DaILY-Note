package com.mobile.daily_note.data.network.retrofit.response


import com.google.gson.annotations.SerializedName

data class ResponseDeleteNote(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)