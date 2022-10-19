package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class GameActivity: AppCompatActivity() {

    lateinit var countDownTextView: TextView
    lateinit var playerHeart1: ImageView
    lateinit var playerHeart2: ImageView
    lateinit var playerHeart3: ImageView
    lateinit var attackButton: Button
    private var countDownTime: Int =0
    private var dead:Boolean = false
    private var life: Int = 0
    private var enemyAttacked: Boolean = false

    private fun initComponent(){
        countDownTextView = findViewById(R.id.countDownTextView)
        playerHeart1 = findViewById(R.id.playerHeart1)
        playerHeart2 = findViewById(R.id.playerHeart2)
        playerHeart3 = findViewById(R.id.playerHeart3)
        attackButton = findViewById(R.id.attackButton)
        countDownTime = 10
        life=3
    }

    private fun initListener(){
        attackButton.setOnClickListener {
            enemyAttacked = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        getSupportActionBar()?.hide()
        initComponent()
        initListener()

        start()

    }

    val timer = object: CountDownTimer(20000, 2000) {
        override fun onTick(millisUntilFinished: Long) {
            Log.e("ONTIMER","Timer Start time $countDownTime")
            countDownTime -= 1
            countDownTextView.setText(countDownTime.toString())
            if(enemyAttacked){
//                Cek jawaban dulu baru ganti ke false
                enemyAttacked = false
                cancel()
            }
        }

        override fun onFinish() {
            countDownTextView.setText("0")
            dead = true

            if(!enemyAttacked) {
                if (playerHeart1.isVisible) {
                    playerHeart1.visibility = View.GONE
                } else {
                    if (playerHeart2.isVisible) {
                        playerHeart2.visibility = View.GONE
                    } else {
                        playerHeart3.visibility = View.GONE
                        countDownTextView.setText("GAME OVER")
                    }
                }
            }
        }
    }

    fun start(){
        var lifes = 3
        var handler = Handler()

        var runnable = Runnable{
            run {
                Log.e("LIFE","Handler Work!")
                synchronized(this){
                    try {
                        while (life>0){
                            timer.start()
                            Thread.sleep(21000)
                            Log.e("DEAD","$dead")
                            if(dead) life --
                            dead = false
                            countDownTime = 10
                        }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                handler.post(Runnable{
                    run{
                        Log.e("LIFE","handler post")
                    }
                })
                Log.e("LIFE","end run")

            }
        }

        val thread = Thread(runnable)
        thread.start()

//        var c= Runnable {
//            Log.e("LIFE","Handler Work!")
//        }
//
//        var hand = Handler()
//        hand.postDelayed(c,1000)
//        while (life>0){
//            Log.e("LIFE",life.toString())
//            var dead = false
//            timer.start()
//            var c= Runnable {
//                if(dead) life--
//            }
//
//            var hand = Handler()
//                hand.postDelayed(c,21000)
//
//            Log.e("DEAD",dead.toString())
//
//        }
    }

}