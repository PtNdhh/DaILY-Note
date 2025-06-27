package com.mobile.daily_note.data.local

data class UserModel(
    var name: String,
    var email: String,
    var token: String,
    var imgUri: String,
    var isLogin: Boolean = false,
    var isDark: Boolean = false
)
