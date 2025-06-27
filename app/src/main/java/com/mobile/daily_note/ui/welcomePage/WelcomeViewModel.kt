package com.mobile.daily_note.ui.welcomePage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.ResponseCreateNote
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeViewModel (private val pref: UserPreference) : ViewModel(){
    fun getSession() = pref.getSession().asLiveData()
}
