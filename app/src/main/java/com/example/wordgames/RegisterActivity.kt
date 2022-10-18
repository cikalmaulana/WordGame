package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var registerButton: Button

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun initListener(){
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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