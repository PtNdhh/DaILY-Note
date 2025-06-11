package com.mobile.daily_note.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.ui.home.ui.archive.ArchiveViewModel
import com.mobile.daily_note.ui.home.ui.notes.NotesViewModel
import com.mobile.daily_note.ui.login.LoginViewModel
import com.mobile.daily_note.ui.main_activity.MainViewModel
import com.mobile.daily_note.ui.register.RegisterViewModel
import okhttp3.internal.http2.Settings

class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel>  create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(ArchiveViewModel::class.java)){
            return ArchiveViewModel(pref)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
