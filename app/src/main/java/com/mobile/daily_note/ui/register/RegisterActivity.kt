package com.mobile.daily_note.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.ActivityRegisterBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val pref = UserPreference.getInstance(application.datastore)
        val registerViewModel = ViewModelProvider(this, ViewModelFactory (pref))[RegisterViewModel::class.java]

        registerViewModel.isSuccess.observe(this){message->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == "Register Success"){
                moveToLogin()
            }
        }

        registerViewModel.isLoading.observe(this){ isLoading ->
            binding.progressBar.visibility =if (isLoading) View.VISIBLE else View.GONE
        }

        binding.createAccountButton.setOnClickListener{
            val username = binding.displayNameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Confirm Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                registerViewModel.register(username, email, password)
            }
        }
        binding.loginHereLink.setOnClickListener {
            moveToLogin()
        }
        
    }

    private fun moveToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    
}