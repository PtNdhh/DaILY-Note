package com.mobile.daily_note.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobile.daily_note.data.local.UserPreference

class SplashViewModel (private val pref: UserPreference) : ViewModel(){
    fun getSession() = pref.getSession().asLiveData()
}