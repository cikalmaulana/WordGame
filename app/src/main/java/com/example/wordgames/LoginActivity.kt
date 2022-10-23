package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wordgames.ui.dashboard.DashboardFragment
import com.example.wordgames.ui.home.HomeFragment

class LoginActivity: AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var loginTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var passwordTextView: TextView

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        loginTextView = findViewById(R.id.loginTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        passwordTextView = findViewById(R.id.passTextView)
    }

    private fun initListener(){
        val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
        loginButton.setTypeface(playfull)
        registerButton.setTypeface(playfull)
        usernameTextView.setTypeface(playfull)
        passwordTextView.setTypeface(playfull)

        loginButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        loginTextView.setTypeface(airfool)
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