package com.mobile.daily_note.data.network.retrofit.response


import com.google.gson.annotations.SerializedName

data class ResponseUnarchiveNote(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)