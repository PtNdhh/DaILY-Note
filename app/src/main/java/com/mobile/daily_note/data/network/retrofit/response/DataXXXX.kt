package com.mobile.daily_note.data.network.retrofit.response


import com.google.gson.annotations.SerializedName

data class DataXXXX(
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("title")
    val title: String
)