package com.mobile.daily_note.ui.welcomePage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.R
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.ActivityWelcomeBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.login.LoginActivity
import com.mobile.daily_note.ui.login.LoginViewModel
import com.mobile.daily_note.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.loginButton.setOnClickListener {
            val intent =Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.createAccountButton.setOnClickListener {
            val intent =Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}