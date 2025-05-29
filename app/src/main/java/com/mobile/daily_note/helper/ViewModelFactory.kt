package com.mobile.daily_note.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.data.local.UserPreference
import okhttp3.internal.http2.Settings

class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel>  create(modelClass: Class<T>): T{
        TODO()
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
