package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wordgames.databinding.ActivityMainBinding
import com.example.wordgames.ui.dashboard.DashboardFragment

class MainActivity : AppCompatActivity() {

    private var pressedTime: Long =0
    private lateinit var binding: ActivityMainBinding
    lateinit var startButton: Button
    lateinit var textView: TextView

    private fun initComponent(){
        val montserrat = Typeface.createFromAsset(assets, "font/Montserrat.ttf")
        val montserratBold = Typeface.createFromAsset(assets, "font/MontserratBold.ttf")
        startButton = findViewById(R.id.startButton)

        textView = findViewById(R.id.helloTextView)
        textView.setTypeface(montserratBold)

        textView = findViewById(R.id.nameTextView)
        textView.setTypeface(montserrat)

        textView = findViewById(R.id.highScoreTextView)
        textView.setTypeface(montserrat)

        textView = findViewById(R.id.scoreTextView)
        textView.setTypeface(montserratBold)

        textView = findViewById(R.id.rankTextView)
        textView.setTypeface(montserrat)

        textView = findViewById(R.id.myRankTextView)
        textView.setTypeface(montserratBold)
    }

    private fun initListener(){
        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
        initListener()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed(){
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}