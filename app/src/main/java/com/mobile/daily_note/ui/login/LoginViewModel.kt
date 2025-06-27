package com.mobile.daily_note.ui.login

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mobile.daily_note.data.local.UserModel
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.ResponseLogin
import com.mobile.daily_note.data.network.retrofit.response.ResponseRegister
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import com.mobile.daily_note.ui.register.RegisterViewModel
import com.mobile.daily_note.ui.register.RegisterViewModel.Companion
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =  _isLoading

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private var userModel = UserModel("", "", "", "",false, false)

    fun login(email: String, password: String){
        _isLoading.value= true
        val client = ApiConfig.getApiService().login( email, password)
        client.enqueue(object : retrofit2.Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isSuccess.value = "Login Success"

                    val token = response.body()?.data?.accessToken
                    userModel.isLogin = true
                    userModel.email = email
                    userModel.token = token.toString()

                    viewModelScope.launch {
                        pref.saveSession(userModel)
                    }
                }else{
                    _isSuccess.value = "Please enter a valid email and a password with at least 6 characters"
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onfailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}