package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.wordgames.ui.dashboard.DashboardFragment
import com.example.wordgames.ui.home.HomeFragment

class LoginActivity: AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var registerButton: Button

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun initListener(){
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponent()
        initListener()

        getSupportActionBar()?.hide()
    }

    override fun onBackPressed(){
        val intent = Intent(this, LandingPageActivity::class.java)
        // start your next activity
        startActivity(intent)
        finish()
    }
}