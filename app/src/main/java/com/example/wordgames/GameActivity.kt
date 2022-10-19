package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.*
import java.lang.Thread.sleep


class GameActivity: AppCompatActivity() {

    lateinit var countDownTextView: TextView
    lateinit var kataKataTextView: TextView

    lateinit var attackButton: Button
    lateinit var tryAgainButton: Button

    lateinit var playerHeart1: ImageView
    lateinit var playerHeart2: ImageView
    lateinit var playerHeart3: ImageView

    lateinit var dinoImageView: ImageView
    lateinit var enemyImageView: ImageView

    lateinit var enemyHeart1: ImageView
    lateinit var enemyHeart2: ImageView
    lateinit var enemyHeart3: ImageView
    lateinit var enemyHeart4: ImageView
    lateinit var enemyHeart5: ImageView
    lateinit var enemyHeart6: ImageView
    lateinit var enemyHeart7: ImageView
    lateinit var enemyHeart8: ImageView
    lateinit var enemyHeart9: ImageView
    lateinit var enemyHeart10: ImageView

    private var life: Int = 3
    private var isGameRun: Boolean = true
    private var dead:Boolean = false
    private var attack: Boolean = false

    private var enemyHearts = mapOf<String,ImageView>()
    private var playerHearts = mapOf<String,ImageView>()

    private fun initComponent(){
        countDownTextView = findViewById(R.id.countDownTextView)
        kataKataTextView = findViewById(R.id.kataKataTextView)

        tryAgainButton = findViewById(R.id.tryAgainButton)

        dinoImageView = findViewById(R.id.dinoImageView)
        enemyImageView = findViewById(R.id.enemyImageView)

        playerHeart1 = findViewById(R.id.playerHeart1)
        playerHeart2 = findViewById(R.id.playerHeart2)
        playerHeart3 = findViewById(R.id.playerHeart3)
        attackButton = findViewById(R.id.attackButton)

        enemyHeart1 = findViewById(R.id.enemyHeart1)
        enemyHeart2 = findViewById(R.id.enemyHeart2)
        enemyHeart3 = findViewById(R.id.enemyHeart3)
        enemyHeart4 = findViewById(R.id.enemyHeart4)
        enemyHeart5 = findViewById(R.id.enemyHeart5)
        enemyHeart6 = findViewById(R.id.enemyHeart6)
        enemyHeart7 = findViewById(R.id.enemyHeart7)
        enemyHeart8 = findViewById(R.id.enemyHeart8)
        enemyHeart9 = findViewById(R.id.enemyHeart9)
        enemyHeart10 = findViewById(R.id.enemyHeart10)

        enemyHearts = mapOf(
            "enemyHeart1" to enemyHeart1,
            "enemyHeart2" to enemyHeart2,
            "enemyHeart3" to enemyHeart3,
            "enemyHeart4" to enemyHeart4,
            "enemyHeart5" to enemyHeart5,
            "enemyHeart6" to enemyHeart6,
            "enemyHeart7" to enemyHeart7,
            "enemyHeart8" to enemyHeart8,
            "enemyHeart9" to enemyHeart9,
            "enemyHeart10" to enemyHeart10,
        )
        playerHearts = mapOf(
            "playerHeart1" to playerHeart1,
            "playerHeart2" to playerHeart2,
            "playerHeart3" to playerHeart3,
        )

    }

    private fun initListener(){
        attackButton.setOnClickListener {
            attack = true
        }

        tryAgainButton.setOnClickListener{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        getSupportActionBar()?.hide()
        initComponent()
        initListener()

        gameStart()
    }



    fun gameStart(){
        var i = 10
        var attacking = false
        var enemyHeart = 10
        var playerHeart = 3
        val objRunnable = java.lang.Runnable {
            try {
                while(life>0){
                    runOnUiThread{
                        kataKataTextView.setText("Kata Kata Life ke $life")
                    }
                    while(!attack && i>0) {
                        Log.e("WHILE", "While ke $i")
                        Log.e("ATTACK","Attack status $attack")
                        runOnUiThread {
                            if(attack) countDownTextView.setText("Attack!")
                            else countDownTextView.setText(i.toString())
                        }
                        sleep(1000)
                        i--
                    }
                    runOnUiThread {
                        if(attack){
//                            Cek jawaban disini
                            countDownTextView.setText("Attack!")
                            closeEnemyHeart(enemyHeart)
                            enemyHeart--
                            val animation = TranslateAnimation(
                                0.0f, 300.0f,
                                0.0f, 0.0f
                            )
                            animation.setDuration(1000)  // animation duration
                            animation.setRepeatCount(1)  // animation repeat count
                            animation.setRepeatMode(2)

                            dinoImageView.startAnimation(animation)
                            Log.e("ATTACK","Attack status di luar while $attack")

                        }
                        else {
                            countDownTextView.setText("You have been hit!")
                            closePlayerHeart(playerHeart)
                            playerHeart--
                            val animation = TranslateAnimation(
                                0.0f, -300.0f,
                                0.0f, 0.0f
                            )
                            animation.setDuration(1000)  // animation duration
                            animation.setRepeatCount(1)  // animation repeat count
                            animation.setRepeatMode(2)

                            enemyImageView.startAnimation(animation)

                        }
                    }
                    sleep(1000)
                    attack = false
                    Log.e("I", "I Sekarang $i")
                    if(i==0) life--
                    if(life==0) countDownTextView.setText("Game Over!")
                    i = 10
                    Log.e("LIFE","Life sekarang $life")

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val objBgThread = Thread(objRunnable)
        objBgThread.start()
    }

    fun closeEnemyHeart(heart: Int){
        enemyHearts.get("enemyHeart$heart")?.visibility = View.GONE
    }

    fun closePlayerHeart(heart: Int){
        playerHearts.get("playerHeart$heart")?.visibility = View.GONE
    }

}