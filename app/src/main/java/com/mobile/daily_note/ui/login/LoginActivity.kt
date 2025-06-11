package com.mobile.daily_note.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.R
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.ActivityLoginBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.HomeActivity
import com.mobile.daily_note.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreference.getInstance(application.datastore)
        val loginViewModel = ViewModelProvider(this,ViewModelFactory (pref))[LoginViewModel::class.java]

        loginViewModel.isSuccess.observe(this){message->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == "Login Success"){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        loginViewModel.isLoading.observe(this){isLoading ->
            binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.loginButton.setOnClickListener{
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }else if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                loginViewModel.login(email, password)
            }
        }
        binding.createAccountText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}