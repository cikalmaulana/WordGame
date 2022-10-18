package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.wordgames.ui.dashboard.DashboardFragment

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getSupportActionBar()?.hide()
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }
}