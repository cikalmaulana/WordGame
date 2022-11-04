package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class HelpActivity: AppCompatActivity() {

    private var username: String = ""
    private var score: String = ""
    private var level:String = ""
    private var nama:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        getSupportActionBar()?.hide()

        nama = intent.getStringExtra("nama").toString()
        username = intent.getStringExtra("username").toString()
        score = intent.getStringExtra("score").toString()
        level = intent.getStringExtra("level").toString()
    }

    override fun onBackPressed(){
        super.onBackPressed();
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("nama", nama)
        intent.putExtra("username", username)
        intent.putExtra("score", score)
        intent.putExtra("level", level)
        startActivity(intent)
        finish()
    }
}