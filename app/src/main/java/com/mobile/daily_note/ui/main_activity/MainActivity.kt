package com.mobile.daily_note.ui.main_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.ActivityWelcomeBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.HomeActivity
import com.mobile.daily_note.ui.login.LoginActivity
import com.mobile.daily_note.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val pref = UserPreference.getInstance(application.datastore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory (pref))[MainViewModel::class.java]

        binding.createAccountButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.getSession().observe(this){ userData ->
            if (userData.isLogin){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}