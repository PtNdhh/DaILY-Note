package com.mobile.daily_note.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobile.daily_note.data.local.UserPreference

class MainViewModel(private val pref: UserPreference) : ViewModel(){
    fun getSession() = pref.getSession().asLiveData()
}