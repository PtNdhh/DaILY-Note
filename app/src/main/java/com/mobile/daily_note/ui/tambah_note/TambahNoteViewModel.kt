package com.mobile.daily_note.ui.tambah_note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.daily_note.data.local.UserModel
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.ResponseCreateNote
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahNoteViewModel (private val pref: UserPreference) : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =  _isLoading

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    val title = MutableLiveData<String>()

    private var userModel = UserModel("", "", "", "",false, false)

    fun uploadNote(title: String, body: String){
        _isLoading.value = true
        val token = runBlocking {
            pref.getSession().first().token
        }
        val bearerToken = "Bearer $token"
        val client = ApiConfig.getApiService().createNote(bearerToken, title, body)
        client.enqueue(object : Callback<ResponseCreateNote>{
            override fun onResponse(
                call: Call<ResponseCreateNote>,
                response: Response<ResponseCreateNote>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _isSuccess.value = response.body()?.status
                }else{
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<ResponseCreateNote>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTheme (): LiveData<UserModel>{
        return pref.getSession().asLiveData()
    }

    fun setThemeSetting(isDark: Boolean){

        userModel.isDark = isDark

        userModel.email = runBlocking {
            pref.getSession().first().email
        }
        userModel.isLogin = runBlocking {
            pref.getSession().first().isLogin
        }
        userModel.name = runBlocking {
            pref.getSession().first().name
        }
        userModel.token = runBlocking {
            pref.getSession().first().token
        }
        userModel.imgUri = runBlocking {
            pref.getSession().first().imgUri
        }

        viewModelScope.launch {
            pref.saveSession(userModel)
        }
    }

    companion object{
        private const val TAG = "TambahNoteViewModel"
    }
}