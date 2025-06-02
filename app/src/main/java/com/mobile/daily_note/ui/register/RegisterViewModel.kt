package com.mobile.daily_note.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.ResponseRegister
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =  _isLoading

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    fun register(username: String, email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().register(username, email, password)
        client.enqueue(object : retrofit2.Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false

                if(response.isSuccessful){
                    _isSuccess.value = "Register Success"
                }else{
                    _isSuccess.value = "Please enter a valid email and a password with at least 6 characters"
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }

            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onfailure: ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }
}