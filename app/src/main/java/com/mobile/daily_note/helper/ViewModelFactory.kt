package com.mobile.daily_note.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.ui.detail_note.DetailViewModel
import com.mobile.daily_note.ui.home.ui.archive.ArchiveViewModel
import com.mobile.daily_note.ui.home.ui.notes.NotesViewModel
import com.mobile.daily_note.ui.home.ui.profile.ProfileViewModel
import com.mobile.daily_note.ui.login.LoginViewModel
import com.mobile.daily_note.ui.register.RegisterViewModel
import com.mobile.daily_note.ui.splash.SplashViewModel
import com.mobile.daily_note.ui.tambah_note.TambahNoteViewModel
import com.mobile.daily_note.ui.welcomePage.WelcomeViewModel


class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel>  create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(ArchiveViewModel::class.java)){
            return ArchiveViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(TambahNoteViewModel::class.java)){
            return TambahNoteViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(pref)as T
        }else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(pref) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
