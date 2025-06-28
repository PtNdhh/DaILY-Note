package com.mobile.daily_note.ui.home.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.daily_note.data.local.UserModel
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.DataX
import com.mobile.daily_note.data.network.retrofit.response.ResponseGetUser
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel (private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =  _isLoading

    private var userModel = UserModel("", "", "","", false, false)
    private val _result = MutableLiveData<DataX>()
    val result: LiveData<DataX> = _result

    init {
        getUserLogged()
    }

    private fun getUserLogged(){
        val token = runBlocking {
            pref.getSession().first().token
        }
        val bearerToken = "Bearer $token"
        val client = ApiConfig.getApiService().getUser(bearerToken)

        client.enqueue(object : Callback<ResponseGetUser> {
            override fun onResponse(
                call: Call<ResponseGetUser>,
                response: Response<ResponseGetUser>
            ) {
                if (response.isSuccessful){
                    _result.value = response.body()?.data
                }else{
                    Log.e(TAG, "onfailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<ResponseGetUser>, t: Throwable) {
                Log.e(TAG, "onfailure: ${t.message.toString()}")
            }
        })

    }

    fun getTheme (): LiveData<UserModel>{
        return pref.getSession().asLiveData()
    }

    fun setUriImage(uriImage: String){
        userModel.imgUri = uriImage

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
        userModel.isDark = runBlocking {
            pref.getSession().first().isDark
        }

        viewModelScope.launch {
            pref.saveSession(userModel)
        }
    }

    fun setThemeSetting(isDark: Boolean){
        val isLogin = runBlocking {
            pref.getSession().first().isLogin
        }
        val email= runBlocking {
            pref.getSession().first().email
        }
        val token = runBlocking {
            pref.getSession().first().token
        }
        val uri = runBlocking {
            pref.getSession().first().imgUri
        }
        val name = runBlocking {
            pref.getSession().first().name
        }
        userModel.email = email
        userModel.isLogin = isLogin
        userModel.name = name
        userModel.token = token
        userModel.isDark = isDark
        userModel.imgUri = uri

        viewModelScope.launch {
            pref.saveSession(userModel)
        }
    }

    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }


    companion object{
        private const val TAG = "ProfileViewModel"
    }
}
