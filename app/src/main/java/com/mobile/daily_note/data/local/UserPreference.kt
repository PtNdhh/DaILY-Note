package com.mobile.daily_note.data.local

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "session")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    private val NAME_KEY = stringPreferencesKey("name")
    private val URI_KEY = stringPreferencesKey("uri")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val LOGIN_KEY = booleanPreferencesKey("isLogin")
    private val DARK_KEY = booleanPreferencesKey("isDark")


    suspend fun saveSession(user: UserModel){
        dataStore.edit {
            it[NAME_KEY] = user.name
            it[EMAIL_KEY] =  user.email
            it[TOKEN_KEY] = user.token
            it[LOGIN_KEY] = user.isLogin
            it[DARK_KEY] = user.isDark
            it[URI_KEY] = user.imgUri
        }
    }

    fun getSession():  Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[NAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[TOKEN_KEY] ?: "",
                it[URI_KEY]?:"",
                it[LOGIN_KEY] ?: false,
                it[DARK_KEY] ?: false
            )
        }
    }

    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}