package com.mobile.daily_note.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.mobile.daily_note.R
import com.mobile.daily_note.databinding.ActivitySplashBinding
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.HomeActivity
import com.mobile.daily_note.ui.login.LoginActivity
import com.mobile.daily_note.ui.welcomePage.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        playAnimation()
        val pref = UserPreference.getInstance(application.datastore)
        val SplashViewModel = ViewModelProvider(this, ViewModelFactory (pref))[SplashViewModel::class.java]

        SplashViewModel.getSession().observe(this){userData ->
            val intent = if (userData.isLogin){
                Intent (this, HomeActivity::class.java)
            }else{
               Intent (this, WelcomeActivity::class.java)
            }
            lifecycleScope.launch {
                delay(2000)
                startActivity(intent)
                finish()
            }
        }

    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imgLogo, View.TRANSLATION_Y, -30f, 30f).apply {
            duration = 800
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val logo = ObjectAnimator.ofFloat(binding.imgLogo, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(logo, title)
            start()
        }
    }
}