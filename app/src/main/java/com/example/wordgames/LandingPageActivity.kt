package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LandingPageActivity: AppCompatActivity() {

    private var pressedTime: Long =0
    lateinit var loginButton: Button

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
    }

    private fun initListener(){
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        initComponent()
        initListener()

        getSupportActionBar()?.hide()
    }

    override fun onBackPressed(){
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}