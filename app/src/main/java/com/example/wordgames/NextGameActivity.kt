package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.wordgames.ui.dashboard.DashboardFragment

class NextGameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_game)

        getSupportActionBar()?.hide()
    }
}