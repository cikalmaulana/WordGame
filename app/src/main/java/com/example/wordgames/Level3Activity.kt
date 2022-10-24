package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.*
import kotlin.random.Random


class Level3Activity: AppCompatActivity() {

    lateinit var countDownTextView: TextView
    lateinit var kataKataTextView: TextView
    lateinit var scoreTextView: TextView
    lateinit var playerNameTextView: TextView
    lateinit var enemyNameTextView: TextView

    lateinit var inputEditText: EditText

    lateinit var attackButton: Button
    lateinit var tryAgainButton: Button
    lateinit var nextGameButton:Button
    lateinit var level2Button: Button
    lateinit var backHomeButton: Button

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

    lateinit var sound: MediaPlayer

    private var life: Int = 3
    private var isGameRun: Boolean = true
    private var attack: Boolean = false
    private var wrongAnswer: Boolean = false
    private var enemyHeart: Int = 1
    private var i: Int = 11
    private var time: Int = 11
    private var level:Int = 1
    private var indexArrayKata: Int = 31
    private var score: Int = 600

    private var enemyHearts = mapOf<String,ImageView>()
    private var playerHearts = mapOf<String,ImageView>()

    private var arrKataTemplate: ArrayList<String> = arrayListOf("Bahtera", "Buana", "Distraksi", "Lembayung", "Papan", "Penggaris", "Buku", "Sapu", "Sampah",
        "Gunting", "Komputer", "Sepeda", "Kulkas", "Matahari", "Bulan", "Piring", "Sendok", "Televisi", "Gelas", "Sabun",
        "Sikat", "Kacamata", "Mesin", "Tidur", "Setrika", "Kaus", "Kemeja", "Kursi", "Celana", "Telepon","Gigi","Pensin","Penghapus", "Ponsel", "Lemari", "Jam")

    private var arrKata: ArrayList<String> = arrayListOf("Bahtera", "Buana", "Distraksi", "Lembayung", "Papan", "Penggaris", "Buku", "Sapu", "Sampah",
        "Gunting", "Komputer", "Sepeda", "Kulkas", "Matahari", "Bulan", "Piring", "Sendok", "Televisi", "Gelas", "Sabun",
        "Sikat", "Kacamata", "Mesin", "Tidur", "Setrika", "Kaus", "Kemeja", "Kursi", "Celana", "Telepon","Gigi","Pensin","Penghapus", "Ponsel", "Lemari", "Jam")


    private var subArrKata: ArrayList<String> = arrayListOf()

    private fun initComponent(){
        countDownTextView = findViewById(R.id.countDownTextView)
        kataKataTextView = findViewById(R.id.kataKataTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        playerNameTextView = findViewById(R.id.playerNameTextView)
        enemyNameTextView = findViewById(R.id.enemyNameTextView)

        inputEditText = findViewById(R.id.inputEditText)

        tryAgainButton = findViewById(R.id.tryAgainButton)
        nextGameButton = findViewById(R.id.nextGameButton)
        level2Button = findViewById(R.id.level2Button)
        backHomeButton = findViewById(R.id.backHomeButton)

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

        //Ambil level dari activity sebelumnya, masukin ke sini
    }

    private fun initListener(){
        val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
        countDownTextView.setTypeface(playfull)
        kataKataTextView.setTypeface(playfull)
        scoreTextView.setTypeface(playfull)
        playerNameTextView.setTypeface(playfull)
        enemyNameTextView.setTypeface(playfull)
        attackButton.setTypeface(playfull)
        tryAgainButton.setTypeface(playfull)
        nextGameButton.setTypeface(playfull)
        level2Button.setTypeface(playfull)
        backHomeButton.setTypeface(playfull)

        level2Button.setOnClickListener {
            val intent = Intent(this@Level3Activity, Level2Activity::class.java)
            startActivity(intent)
            finish()
        }

        backHomeButton.setOnClickListener {
            sound.stop()
            val intent = Intent(this@Level3Activity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        inputEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                val input: String = inputEditText.text.toString().lowercase().replace("\\s".toRegex(), "")
                val kata: String = kataKataTextView.text.toString().lowercase().replace("\\s".toRegex(), "")
                var benar = input == kata
                Log.e("CEKKEBENARAN", kataKataTextView.text.toString().lowercase() + " dan " + inputEditText.text.toString().lowercase()+ " == "+ benar.toString())
                if(input == kata) attack = true
                else wrongAnswer = true
                inputEditText.setText("")
                return@OnKeyListener true
            }
            false
        })

        attackButton.setOnClickListener {
            val input: String = inputEditText.text.toString().lowercase().replace("\\s".toRegex(), "")
            val kata: String = kataKataTextView.text.toString().lowercase().replace("\\s".toRegex(), "")
            var benar = input == kata
            Log.e("CEKKEBENARAN", kataKataTextView.text.toString().lowercase() + " dan " + inputEditText.text.toString().lowercase()+ " == "+ benar.toString())
            if(input == kata) attack = true
            else wrongAnswer = true
            inputEditText.setText("")
        }

        nextGameButton.setOnClickListener {
            Log.e("NEXTLEVEL","Clicked!")
            if (level<3){
                nextGameButton.visibility = View.INVISIBLE
                kataKataTextView.visibility = View.VISIBLE

                enemyHeart = 10
                while(enemyHeart>0){
                    openEnemyHeart(enemyHeart)
                    enemyHeart--
                }

                life = 3
                isGameRun = true
                enemyHeart = 1
                level++
                gameStart()
            }else kataKataTextView.setText("SELAMAT KAMU MENANG!")

        }

        tryAgainButton.bringToFront()

        nextGameButton.bringToFront()

        tryAgainButton.setOnClickListener{
            Log.e("TRYAGAIN","Clicked!")
            tryAgainButton.visibility = View.INVISIBLE

            var playerHeart = 3
            while(playerHeart>0){
                openPlayerHeart(playerHeart)
                playerHeart--
            }

            enemyHeart = 10
            while(enemyHeart>0){
                openEnemyHeart(enemyHeart)
                enemyHeart--
            }

            life = 3
            isGameRun = true
            enemyHeart = 1
            indexArrayKata = 31
            dinoImageView.visibility = View.VISIBLE
            enemyImageView.visibility = View.VISIBLE
            kataKataTextView.visibility = View.VISIBLE
            score = 600
            scoreTextView.setText("600")
            initArray()
            gameStart()
        }
    }

//    fun setScore(){
//    }

    fun initArray(){
        Log.e("INIT", "Masuk initarray")
        var index = 0;
        arrKata.removeAll(arrKata)

        arrKata.addAll(listOf("Bahtera", "Buana", "Distraksi", "Lembayung", "Papan", "Penggaris", "Buku", "Sapu", "Sampah",
            "Gunting", "Komputer", "Sepeda", "Kulkas", "Matahari", "Bulan", "Piring", "Sendok", "Televisi", "Gelas", "Sabun",
            "Sikat", "Kacamata", "Mesin", "Tidur", "Setrika", "Kaus", "Kemeja", "Kursi", "Celana", "Telepon","Gigi","Pensin","Penghapus", "Ponsel", "Lemari", "Jam"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_3)

        getSupportActionBar()?.hide()
        initComponent()
        initListener()

        sound = MediaPlayer.create(this,R.raw.soundtrack)
        sound.start()

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        gameStart()
    }

    fun gameStart(){

//        Countdown sebelum mulai

        Log.e( "GAMESTART", "Game Start!")
        var playerHeart = 1
        val objRunnable = java.lang.Runnable {
            try {
                var k = 3
                while(k>=1){
                    runOnUiThread {
                        kataKataTextView.setText("Get Ready...")
                        countDownTextView.setText(k.toString())
                        Log.e("COUNTAWAL","${k.toString()}")
                    }
                    sleep(1000)
                    k--
                }
                runOnUiThread {
                    countDownTextView.setText("Start!")
                }
                sleep(1000)
                while(life>0 && isGameRun){
                    runOnUiThread{
                        val animation = TranslateAnimation(
                            0.0f, 0.0f,
                            0.0f, 30.0f
                        )
                        animation.setDuration(500)  // animation duration
                        animation.setRepeatCount(999)  // animation repeat count
                        animation.setRepeatMode(2)
                        dinoImageView.startAnimation(animation)
                        enemyImageView.startAnimation(animation)

                        Log.i("RANDI",indexArrayKata.toString())
                        Log.i("PJGARRAY",arrKata.size.toString())
                        val randomIndex = Random.nextInt(1,indexArrayKata)
                        val kata = arrKata.get(randomIndex)
                        Log.i("KATASAATINI","Kata Sekarang Adalah $kata")
                        arrKata.removeAt(randomIndex)
                        kataKataTextView.setText(kata)
                        indexArrayKata--
                    }
                    while(!attack && i>0) {
                        Log.e("WHILE", "While ke $i")
                        Log.e("ATTACK","Attack status $attack")
                        runOnUiThread {
                            val str:Int = i-1
                            if(attack && !wrongAnswer) countDownTextView.setText("Attack!")
                            else if(wrongAnswer) countDownTextView.setText("Wrong Answer!!")
                            else countDownTextView.setText(str.toString())
                            wrongAnswer = false
                        }
                        sleep(1000)
                        i--
                    }
                    runOnUiThread {
                        if(attack && i>0){
                            score+=10
                            scoreTextView.setText(score.toString())
                            val animation = TranslateAnimation(
                                0.0f, 300.0f,
                                0.0f, 0.0f
                            )
                            animation.setDuration(1000)  // animation duration
                            animation.setRepeatCount(1)  // animation repeat count
                            animation.setRepeatMode(2)
                            dinoImageView.startAnimation(animation)

                            if(enemyHeart>=10){
                                if(level<3){
                                    closeEnemyHeart(enemyHeart)
                                    kataKataTextView.visibility = View.INVISIBLE
                                    nextGameButton.visibility = View.VISIBLE
                                    countDownTextView.setText("You Win!")
                                    isGameRun = false
                                }else{
                                    isGameRun = false
                                    dinoImageView.visibility = View.INVISIBLE
                                    enemyImageView.visibility = View.INVISIBLE
                                    kataKataTextView.visibility = View.INVISIBLE
                                    countDownTextView.setText("Level 3 Telah Selesai!")
                                }
                            }else{
                                closeEnemyHeart(enemyHeart)
                                countDownTextView.setText("Attack!")
                            }


                            Log.e("ATTACK","Attack status di luar while $attack")
                            enemyHeart++
                        }
                        else {
                            life--
                            countDownTextView.setText("OUCH!!")
                            closePlayerHeart(playerHeart)
                            playerHeart++
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
                    sleep(2000)
                    attack = false
                    Log.e("I", "I Sekarang $i")

                    if(life<=0) {
//                        Tampilin game over
                        runOnUiThread {
                            val animation = TranslateAnimation(
                                0.0f, 0.0f,
                                0.0f, 200.0f
                            )
                            animation.setDuration(2000)  // animation duration
                            animation.setRepeatCount(0)  // animation repeat count
                            animation.setRepeatMode(2)
                            dinoImageView.startAnimation(animation)


                            countDownTextView.setText("You Die!")
                        }
                        sleep(2000)
                        runOnUiThread {
                            kataKataTextView.visibility = View.INVISIBLE
                            tryAgainButton.visibility = View.VISIBLE
                            backHomeButton.visibility = View.VISIBLE
                            dinoImageView.visibility = View.INVISIBLE
                            enemyImageView.visibility = View.INVISIBLE
                        }
                    }
                    i = time
                    Log.e("LIFE","Life sekarang $life")

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val objBgThread = Thread(objRunnable)
        objBgThread.start()
    }

    fun openEnemyHeart(heart: Int){
        enemyHearts.get("enemyHeart$heart")?.visibility = View.VISIBLE
    }

    fun openPlayerHeart(heart: Int){
        playerHearts.get("playerHeart$heart")?.visibility = View.VISIBLE
    }

    fun closeEnemyHeart(heart: Int){
        enemyHearts.get("enemyHeart$heart")?.visibility = View.INVISIBLE
    }

    fun closePlayerHeart(heart: Int){
        playerHearts.get("playerHeart$heart")?.visibility = View.INVISIBLE
    }

    override fun onBackPressed(){
        sound.stop()
        super.onBackPressed();
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}