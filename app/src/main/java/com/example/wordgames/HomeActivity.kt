package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {

    lateinit var charImageView: ImageView
    lateinit var klikTextView: TextView
    lateinit var scoreTextView: TextView
    lateinit var userScoreTextView: TextView
    lateinit var rankTextView: TextView
    lateinit var userRankTextView: TextView
    lateinit var viewScoreButton: TextView
    lateinit var viewHelpButton: TextView
    lateinit var usernameTextView: TextView
    lateinit var layoutStartGame: RelativeLayout

    private var nama:String = ""
    private var score:String = ""
    private var level:String = ""

    private fun initComponent(){
        charImageView = findViewById(R.id.charImageView)
        klikTextView = findViewById(R.id.klikTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        userScoreTextView = findViewById(R.id.userScoreTextView)
        rankTextView = findViewById(R.id.rankTextView)
        userRankTextView = findViewById(R.id.userRankTextView)
        viewScoreButton = findViewById(R.id.viewScoreButton)
        viewHelpButton = findViewById(R.id.viewHelpButton)
        layoutStartGame = findViewById(R.id.layoutStartGame)
        usernameTextView = findViewById(R.id.usernameTextView)
    }

    private fun initListener(){
        val intent = intent
        nama = intent.getStringExtra("nama").toString()
        score = intent.getStringExtra("score").toString()
        level = intent.getStringExtra("leve").toString()
        usernameTextView.setText(nama)
        userScoreTextView.setText(score)
        val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
        klikTextView.setTypeface(playfull)
        scoreTextView.setTypeface(playfull)
        userScoreTextView.setTypeface(playfull)
        rankTextView.setTypeface(playfull)
        userRankTextView.setTypeface(playfull)
        viewScoreButton.setTypeface(playfull)
        viewHelpButton.setTypeface(playfull)
        usernameTextView.setTypeface(playfull)

        layoutStartGame.setOnClickListener {
            val intent = Intent(this@HomeActivity, LevelActivity::class.java)
            startActivity(intent)
            finish()
        }

        val animation = TranslateAnimation(
            0.0f, 700.0f,
            0.0f, 0.0f
        )
        animation.setDuration(4000)  // animation duration
        animation.setRepeatCount(9999)  // animation repeat count
        animation.setRepeatMode(2)

        charImageView.startAnimation(animation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getSupportActionBar()?.hide()

        initComponent()
        initListener()


    }
}