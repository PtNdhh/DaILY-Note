package com.mobile.daily_note.ui.home.ui.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.network.retrofit.response.DataNote
import com.mobile.daily_note.data.network.retrofit.response.ResponseGetNotes
import com.mobile.daily_note.data.network.retrofit.response.ResponseLogin
import com.mobile.daily_note.data.network.retrofit.retrofit.ApiConfig
import com.mobile.daily_note.ui.login.LoginViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesViewModel (private val pref: UserPreference) : ViewModel() {

    private val _listNote = MutableLiveData<List<DataNote>>()
    val listNote: LiveData<List<DataNote>> = _listNote

    init {
        setNotesList()
    }

    fun setNotesList(){
        val token = runBlocking {
            pref.getSession().first().token
        }
        val bearerToken = "Bearer $token"
        val client = ApiConfig.getApiService().getNotes(bearerToken)
        client.enqueue(object : Callback<ResponseGetNotes>{
            override fun onResponse(
                p0: Call<ResponseGetNotes?>,
                p1: Response<ResponseGetNotes?>
            ) {
                if (p1.isSuccessful){
                    _listNote.value = p1.body()?.data
                }else{
                    Log.e(TAG, "onfailure: ${p1.message().toString()}")
                }
            }

            override fun onFailure(
                p0: Call<ResponseGetNotes?>,
                p1: Throwable
            ) {
                Log.e(TAG, "onfailure: ${p1.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "NotesViewModel"
    }
}