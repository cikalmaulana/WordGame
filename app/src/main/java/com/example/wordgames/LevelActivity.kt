package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LevelActivity: AppCompatActivity() {

    lateinit var pilihLevelTextView: TextView
    lateinit var level1Button: Button
    lateinit var level2Button: Button
    lateinit var level3Button: Button

    private fun initComponent(){
        pilihLevelTextView = findViewById(R.id.pilihLevelTextView)
        level1Button = findViewById(R.id.level1Button)
    }

    private fun initListener(){
        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        pilihLevelTextView.setTypeface(airfool)

        level1Button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        getSupportActionBar()?.hide()

        initComponent()
        initListener()

    }

    override fun onBackPressed(){
        super.onBackPressed();
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}