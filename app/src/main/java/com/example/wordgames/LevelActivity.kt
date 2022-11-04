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

    private var isLevel2Unlock: Boolean = true
    private var isLevel3Unlock: Boolean = true

    private var username: String = ""
    private var score: String = ""
    private var level:String = ""
    private var nama:String = ""

    private fun initComponent(){
        pilihLevelTextView = findViewById(R.id.pilihLevelTextView)
        level1Button = findViewById(R.id.level1Button)
        level2Button = findViewById(R.id.level2Button)
        level3Button = findViewById(R.id.level3Button)
        warningTextView = findViewById(R.id.warningTextView)
    }

    private fun initListener(){
        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        pilihLevelTextView.setTypeface(airfool)

        nama = intent.getStringExtra("nama").toString()
        username = intent.getStringExtra("username").toString()
        score = intent.getStringExtra("score").toString()
        level = intent.getStringExtra("level").toString()

        level1Button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("username", username)
            intent.putExtra("score", score)
            intent.putExtra("level", level)
            startActivity(intent)
            finish()
        }

        level2Button.setOnClickListener {
            if(level>= 2.toString()){
                val intent = Intent(this, Level2Activity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("username", username)
                intent.putExtra("score", score)
                intent.putExtra("level", level)
                startActivity(intent)
                finish()
            }else{
                warningTextView.setText("Selesaikan Level 1 Dahulu")
                warningTextView.visibility = View.VISIBLE
            }
        }

        level3Button.setOnClickListener {
            if(level>= 3.toString()){
                val intent = Intent(this, Level3Activity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("username", username)
                intent.putExtra("score", score)
                intent.putExtra("level", level)
                startActivity(intent)
                finish()
            }else{
                warningTextView.setText("Selesaikan Level 2 Dahulu")
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
        intent.putExtra("nama", nama)
        intent.putExtra("username", username)
        intent.putExtra("score", score)
        intent.putExtra("level", level)
        startActivity(intent)
        finish()
    }
}