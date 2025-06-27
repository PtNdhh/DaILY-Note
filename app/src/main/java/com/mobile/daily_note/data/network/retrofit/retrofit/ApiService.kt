package com.mobile.daily_note.data.network.retrofit.retrofit

import com.mobile.daily_note.data.network.retrofit.response.ResponseArchiveNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseCreateNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseDeleteNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseGetArchiveNotes
import com.mobile.daily_note.data.network.retrofit.response.ResponseGetNotes
import com.mobile.daily_note.data.network.retrofit.response.ResponseGetUser
import com.mobile.daily_note.data.network.retrofit.response.ResponseLogin
import com.mobile.daily_note.data.network.retrofit.response.ResponseRegister
import com.mobile.daily_note.data.network.retrofit.response.ResponseUnarchiveNote
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("notes")
    fun getNotes(
        @Header("Authorization") token: String
    ): Call<ResponseGetNotes>

    @GET("users/me")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<ResponseGetUser>

    @FormUrlEncoded
    @POST("notes")
    fun createNote(
        @Header("Authorization") token: String,
        @Field("title") title: String,
        @Field("body") body: String
    ): Call<ResponseCreateNote>

    @GET("notes/archived")
    fun getArchiveNotes(
        @Header("Authorization") token: String,

    ): Call<ResponseGetArchiveNotes>

    @GET("notes/{note_id}")
    fun getNoteById(
        @Header("Authorization") token: String,
        @Query("note_id") noteId: String
    ): Call<ResponseGetNotes>

    @POST("notes/{note_id}/archive")
    fun archiveNote(
        @Header("Authorization") token: String,
        @Path("note_id") noteId: String
    ): Call<ResponseArchiveNote>

    @POST("notes/{note_id}/unarchive")
    fun unarchiveNote(
        @Header("Authorization") token: String,
        @Path("note_id") noteId: String
    ): Call<ResponseUnarchiveNote>

    @DELETE("notes/{note_id}")
    fun deleteNote(
        @Header("Authorization") token: String,
        @Path("note_id") noteId: String
    ): Call<ResponseDeleteNote>








}