package com.mobile.daily_note.ui.register

import androidx.lifecycle.ViewModel
import com.mobile.daily_note.data.network.retrofit.response.ResponseRegister
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    fun register(username: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(username, email, password)
        client.enqueue(object : retrofit2.Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}