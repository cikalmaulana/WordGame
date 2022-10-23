package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LevelActivity: AppCompatActivity() {

    lateinit var pilihLevelTextView: TextView
    lateinit var level1Button: Button
    lateinit var level2Button: Button
    lateinit var level3Button: Button
    lateinit var warningTextView: TextView

    private var isLevel2Unlock: Boolean = false

    private fun initComponent(){
        pilihLevelTextView = findViewById(R.id.pilihLevelTextView)
        level1Button = findViewById(R.id.level1Button)
        level2Button = findViewById(R.id.level2Button)
        warningTextView = findViewById(R.id.warningTextView)
    }

    private fun initListener(){
        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        pilihLevelTextView.setTypeface(airfool)

        level1Button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        level2Button.setOnClickListener {
            if(isLevel2Unlock){
                val intent = Intent(this, Level2Activity::class.java)
                startActivity(intent)
                finish()
            }else{
                warningTextView.visibility = View.VISIBLE
            }
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
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}