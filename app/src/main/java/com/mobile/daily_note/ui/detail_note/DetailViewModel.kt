package com.mobile.daily_note.ui.detail_note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.ResponseArchiveNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseDeleteNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseUnarchiveNote
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =  _isLoading

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private val token = runBlocking {
        pref.getSession().first().token
    }
    private val bearerToken = "Bearer $token"

    private fun getToken(): String {
        val token = runBlocking {
            pref.getSession().first().token
        }
        return "Bearer $token"
    }

    fun unArchiveNote(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().unarchiveNote(getToken(), id)
        client.enqueue(object : Callback<ResponseUnarchiveNote> {
            override fun onResponse(
                call: Call<ResponseUnarchiveNote?>,
                response: Response<ResponseUnarchiveNote?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSuccess.value = response.body()?.status
                } else {
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(
                call: Call<ResponseUnarchiveNote?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    fun deleteNote(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteNote(getToken(), id)
        client.enqueue(object : Callback<ResponseDeleteNote> {
            override fun onResponse(
                call: Call<ResponseDeleteNote?>,
                response: Response<ResponseDeleteNote?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _isSuccess.value = response.body()?.status
                }else{
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(
                call: Call<ResponseDeleteNote?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })


    }

    fun archiveNote(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().archiveNote(getToken(), id)
        client.enqueue(object : Callback<ResponseArchiveNote> {
            override fun onResponse(
                call: Call<ResponseArchiveNote>,
                response: Response<ResponseArchiveNote>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _isSuccess.value = response.body()?.status
                }else{
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(
                call: Call<ResponseArchiveNote>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}