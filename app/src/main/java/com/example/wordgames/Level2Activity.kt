package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*
import kotlin.random.Random


class Level2Activity: AppCompatActivity() {

    lateinit var mTTS: TextToSpeech

    lateinit var countDownTextView: TextView
    lateinit var kataKataTextView: TextView
    lateinit var scoreTextView: TextView
    lateinit var playerNameTextView: TextView
    lateinit var enemyNameTextView: TextView
    lateinit var tulisanSkorTextView: TextView

    lateinit var inputEditText: EditText

    lateinit var attackButton: Button
    lateinit var tryAgainButton: Button
    lateinit var nextGameButton:Button
    lateinit var level2Button: Button
    lateinit var backHomeButton: Button
    lateinit var speakButton: Button

    lateinit var playerHeart1: ImageView
    lateinit var playerHeart2: ImageView
    lateinit var playerHeart3: ImageView

    lateinit var dinoImageView: ImageView
    lateinit var enemyImageView: ImageView
    lateinit var floodbgImageView: ImageView

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

    lateinit var scratchImageView: ImageView
    lateinit var scratchMusuhImageView: ImageView

    lateinit var sound: MediaPlayer

    lateinit var kata1: MediaPlayer
    lateinit var kata2: MediaPlayer
    lateinit var kata3: MediaPlayer
    lateinit var kata4: MediaPlayer
    lateinit var kata5: MediaPlayer
    lateinit var kata6: MediaPlayer
    lateinit var kata7: MediaPlayer
    lateinit var kata8: MediaPlayer
    lateinit var kata9: MediaPlayer
    lateinit var kata10: MediaPlayer
    lateinit var kata11: MediaPlayer
    lateinit var kata12: MediaPlayer
    lateinit var kata13: MediaPlayer
    lateinit var kata14: MediaPlayer
    lateinit var kata15: MediaPlayer
    lateinit var kata16: MediaPlayer
    lateinit var kata17: MediaPlayer
    lateinit var kata18: MediaPlayer
    lateinit var kata19: MediaPlayer
    lateinit var kata20: MediaPlayer
    lateinit var kata21: MediaPlayer
    lateinit var kata22: MediaPlayer
    lateinit var kata23: MediaPlayer
    lateinit var kata24: MediaPlayer
    lateinit var kata25: MediaPlayer
    lateinit var kata26: MediaPlayer
    lateinit var kata27: MediaPlayer
    lateinit var kata28: MediaPlayer
    lateinit var kata29: MediaPlayer
    lateinit var kata30: MediaPlayer
    lateinit var kata31: MediaPlayer
    lateinit var kata32: MediaPlayer
    lateinit var kata33: MediaPlayer
    lateinit var kata34: MediaPlayer
    lateinit var kata35: MediaPlayer
    lateinit var kata36: MediaPlayer

    private var life: Int = 3
    private var isGameRun: Boolean = true
    private var attack: Boolean = false
    private var wrongAnswer: Boolean = false
    private var enemyHeart: Int = 1
    private var i: Int = 21
    private var time: Int = 21
    private var level:Int = 1
    private var indexArrayKata: Int = 32
    private var score: Int = 0

    private var username: String = ""
    private var scoreLast: String = ""
    private var levelLast:String =""
    private var nama:String=""
    private var kataKeluar: String = ""
    private var randomIndex: Int = 0

    private var enemyHearts = mapOf<String,ImageView>()
    private var playerHearts = mapOf<String,ImageView>()
    private var kataSound:HashMap<String,MediaPlayer> = hashMapOf<String,MediaPlayer>()

    private var arrKataTemplate: ArrayList<String> = arrayListOf("Daun Hijau", "Kayu Bakar", "Air Hujan", "Asap Motor", "Pohon Pinus", "Hutan Kota", "Laut Merah", "Tinggi Gunung", "Awan Gelap", "Langit Sore",
                                "Tanah Kering", "Pupuk Organik", "Sungai Kotor", "Angin Segar", "Satwa Liar", "Limbah Pabrik", "Udara Bersih", "Alam Lestari", "Polusi Udara", "Ikan Laut", "Bambu Kuning",
                                "Bunga Mawar", "Ulat Sagu", "Akar Pohon", "Hutan Gundul", "Matahari Terbit", "Rumah Kaca", "Sepeda Lipat", "Bus Kota", "Kereta Api","Daun Hijau", "Kayu Bakar", "Satwa Liar")

    private var arrKata: ArrayList<String> = arrayListOf("Daun Hijau", "Kayu Bakar", "Air Hujan", "Asap Motor", "Pohon Pinus", "Hutan Kota", "Laut Merah", "Tinggi Gunung", "Awan Gelap", "Langit Sore",
        "Tanah Kering", "Pupuk Organik", "Sungai Kotor", "Angin Segar", "Satwa Liar", "Limbah Pabrik", "Udara Bersih", "Alam Lestari", "Polusi Udara", "Ikan Laut", "Bambu Kuning",
        "Bunga Mawar", "Ulat Sagu", "Akar Pohon", "Hutan Gundul", "Matahari Terbit", "Rumah Kaca", "Sepeda Lipat", "Bus Kota", "Kereta Api","Daun Hijau", "Kayu Bakar", "Satwa Liar")

    private var subArrKata: ArrayList<String> = arrayListOf()

    private fun initComponent(){
        countDownTextView = findViewById(R.id.countDownTextView)
        kataKataTextView = findViewById(R.id.kataKataTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        playerNameTextView = findViewById(R.id.playerNameTextView)
        enemyNameTextView = findViewById(R.id.enemyNameTextView)
        tulisanSkorTextView = findViewById(R.id.tulisanSkorTextView)

        inputEditText = findViewById(R.id.inputEditText)

        tryAgainButton = findViewById(R.id.tryAgainButton)
        nextGameButton = findViewById(R.id.nextGameButton)
        level2Button = findViewById(R.id.level2Button)
        backHomeButton = findViewById(R.id.backHomeButton)
        speakButton = findViewById(R.id.speakButton)

        dinoImageView = findViewById(R.id.dinoImageView)
        enemyImageView = findViewById(R.id.enemyImageView)
        scratchImageView = findViewById(R.id.scratchImageView)
        scratchMusuhImageView = findViewById(R.id.scratchMusuhImageView)
        floodbgImageView = findViewById(R.id.floodbgImageView)

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
2
        kata1 = MediaPlayer.create(this,R.raw.frasa1)
        kata2 = MediaPlayer.create(this,R.raw.frasa2)
        kata3 = MediaPlayer.create(this,R.raw.frasa3)
        kata4 = MediaPlayer.create(this,R.raw.frasa4)
        kata5 = MediaPlayer.create(this,R.raw.frasa5)
        kata6 = MediaPlayer.create(this,R.raw.frasa6)
        kata7 = MediaPlayer.create(this,R.raw.frasa7)
        kata8 = MediaPlayer.create(this,R.raw.frasa8)
        kata9 = MediaPlayer.create(this,R.raw.frasa9)
        kata10 = MediaPlayer.create(this,R.raw.frasa10)
        kata11 = MediaPlayer.create(this,R.raw.frasa11)
        kata12 = MediaPlayer.create(this,R.raw.frasa12)
        kata13 = MediaPlayer.create(this,R.raw.frasa13)
        kata14 = MediaPlayer.create(this,R.raw.frasa14)
        kata15 = MediaPlayer.create(this,R.raw.frasa15)
        kata16 = MediaPlayer.create(this,R.raw.frasa16)
        kata17 = MediaPlayer.create(this,R.raw.frasa17)
        kata18 = MediaPlayer.create(this,R.raw.frasa18)
        kata19 = MediaPlayer.create(this,R.raw.frasa19)
        kata20 = MediaPlayer.create(this,R.raw.frasa20)
        kata21 = MediaPlayer.create(this,R.raw.frasa21)
        kata22 = MediaPlayer.create(this,R.raw.frasa22)
        kata23 = MediaPlayer.create(this,R.raw.frasa23)
        kata24 = MediaPlayer.create(this,R.raw.frasa24)
        kata25 = MediaPlayer.create(this,R.raw.frasa25)
        kata26 = MediaPlayer.create(this,R.raw.frasa26)
        kata27 = MediaPlayer.create(this,R.raw.frasa27)
        kata28 = MediaPlayer.create(this,R.raw.frasa28)
        kata29 = MediaPlayer.create(this,R.raw.frasa29)
        kata30 = MediaPlayer.create(this,R.raw.frasa30)
        kata31 = MediaPlayer.create(this,R.raw.frasa1)
        kata32 = MediaPlayer.create(this,R.raw.frasa2)
        kata33 = MediaPlayer.create(this,R.raw.frasa15)
        kata34 = MediaPlayer.create(this,R.raw.kata34)
        kata35 = MediaPlayer.create(this,R.raw.kata35)
        kata36 = MediaPlayer.create(this,R.raw.kata36)

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

        kataSound = hashMapOf(
            "Daun Hijau" to kata1,
            "Kayu Bakar" to kata2,
            "Air Hujan" to kata3,
            "Asap Motor" to kata4,
            "Pohon Pinus" to kata5,
            "Hutan Kota" to kata6,
            "Laut Merah" to kata7,
            "Tinggi Gunung" to kata8,
            "Awan Gelap" to kata9,
            "Langit Sore" to kata10,
            "Tanah Kering" to kata11,
            "Pupuk Organik" to kata12,
            "Sungai Kotor" to kata13,
            "Angin Segar" to kata14,
            "Satwa Liar" to kata15,
            "Limbah Pabrik" to kata16,
            "Udara Bersih" to kata17,
            "Alam Lestari" to kata18,
            "Polusi Udara" to kata19,
            "Ikan Laut" to kata20,
            "Bambu Kuning" to kata21,
            "Bunga Mawar" to kata22,
            "Ulat Sagu" to kata23,
            "Akar Pohon" to kata24,
            "Hutan Gundul" to kata25,
            "Matahari Terbit" to kata26,
            "Rumah Kaca" to kata27,
            "Sepeda Lipat" to kata28,
            "Bus Kota" to kata29,
            "Kereta Api" to kata30
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
        speakButton.setTypeface(playfull)
        tulisanSkorTextView.setTypeface(playfull)
        attackButton.setTypeface(playfull)

        username = intent.getStringExtra("username").toString()
        scoreLast = intent.getStringExtra("score").toString()
        nama = intent.getStringExtra("nama").toString()
        levelLast = intent.getStringExtra("level").toString()

        speakButton.visibility = View.GONE

        level2Button.setOnClickListener {
            sound.stop()
            val intent = Intent(this@Level2Activity, Level3Activity::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("username", username)
            intent.putExtra("score", scoreLast)
            intent.putExtra("level", levelLast)
            startActivity(intent)
            finish()
        }

        backHomeButton.setOnClickListener {
            sound.stop()
            val intent = Intent(this@Level2Activity, HomeActivity::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("username", username)
            intent.putExtra("score", scoreLast)
            intent.putExtra("level", levelLast)
            startActivity(intent)
            finish()
        }

        inputEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                val input: String = inputEditText.text.toString().lowercase().replace("\\s".toRegex(), "")
                val kata: String = kataKeluar.lowercase().replace("\\s".toRegex(), "")
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
            val kata: String = kataKeluar.lowercase().replace("\\s".toRegex(), "")
            var benar = input == kata
            Log.e("CEKKEBENARAN", kataKataTextView.text.toString().lowercase() + " dan " + inputEditText.text.toString().lowercase()+ " == "+ benar.toString())
            if(input == kata) attack = true
            else wrongAnswer = true
            inputEditText.setText("")
        }

        nextGameButton.setOnClickListener {
            Log.e("NEXTLEVEL","Clicked!")
            if (level<3){
                if(scoreLast.toInt() < score+300){
                    scoreLast = score.toString()
                    putScore()
                }
                nextGameButton.visibility = View.INVISIBLE
                kataKataTextView.visibility = View.VISIBLE
                speakButton.visibility = View.GONE
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
            backHomeButton.visibility = View.INVISIBLE
            scratchImageView.visibility = View.INVISIBLE
            scratchMusuhImageView.visibility = View.INVISIBLE
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
            indexArrayKata = 32
            dinoImageView.visibility = View.VISIBLE
            enemyImageView.visibility = View.VISIBLE
            kataKataTextView.visibility = View.VISIBLE
            score = 0
            scoreTextView.setText("0")
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

        arrKata.addAll(listOf("Daun Hijau", "Kayu Bakar", "Air Hujan", "Asap Motor", "Pohon Pinus", "Hutan Kota", "Laut Merah", "Tinggi Gunung", "Awan Gelap", "Langit Sore",
            "Tanah Kering", "Pupuk Organik", "Sungai Kotor", "Angin Segar", "Satwa Liar", "Limbah Pabrik", "Udara Bersih", "Alam Lestari", "Polusi Udara", "Ikan Laut", "Bambu Kuning",
            "Bunga Mawar", "Ulat Sagu", "Akar Pohon", "Hutan Gundul", "Matahari Terbit", "Rumah Kaca", "Sepeda Lipat", "Bus Kota", "Kereta Api","Daun Hijau", "Kayu Bakar", "Satwa Liar"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_2)

        speakButton = findViewById(R.id.speakButton)

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            Log.e("STATUS", status.toString())
            mTTS.setLanguage(Locale("id","ID"))
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale("id","ID")
            }
        })

        getSupportActionBar()?.hide()
        initComponent()
        initListener()

        sound = MediaPlayer.create(this,R.raw.soundtrack)
//        sound.start()

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        gameStart()
    }

    fun gameStart(){

//        Countdown sebelum mulai

        Log.e( "GAMESTART", "Game Start!")
        var playerHeart = 1
        val objRunnable = java.lang.Runnable {
            try {
                floodbgImageView.visibility = View.INVISIBLE
                var k = 3
                while(k>=1){
                    runOnUiThread {
                        kataKataTextView.visibility = View.VISIBLE
                        kataKataTextView.setText("Cegah Monster Membuang Sampah Sembarangan!")
                        countDownTextView.setText(k.toString())
                        Log.e("COUNTAWAL","${k.toString()}")
                    }
                    sleep(1000)
                    k--
                }
                runOnUiThread {
                    kataKataTextView.setText("Mulai!")
                }
                sleep(1000)
                while(life>0 && isGameRun){
                    scratchImageView.visibility = View.INVISIBLE
                    scratchMusuhImageView.visibility = View.INVISIBLE
                    runOnUiThread{
                        val animation = TranslateAnimation(
                            0.0f, 0.0f,
                            0.0f, 30.0f
                        )
                        animation.setDuration(500)  // animation duration
                        animation.setRepeatCount(999)  // animation repeat count
                        animation.setRepeatMode(2)

                        val animation2 = TranslateAnimation(
                            0.0f, 30.0f,
                            0.0f, 30.0f
                        )
                        animation2.setDuration(500)  // animation duration
                        animation2.setRepeatCount(999)  // animation repeat count
                        animation2.setRepeatMode(2)

                        dinoImageView.startAnimation(animation)
                        enemyImageView.startAnimation(animation2)

                        Log.i("RANDI",indexArrayKata.toString())
                        Log.i("PJGARRAY",arrKata.size.toString())
                        randomIndex = Random.nextInt(1,indexArrayKata)
                        val kata = arrKata.get(randomIndex)
                        val x =  kataSound.get("$kata")
                        kataKeluar = kata
                        Log.i("KATASAATINI","Kata Sekarang Adalah $kata")
                        arrKata.removeAt(randomIndex)
                        kataKataTextView.setText(kata)
                        kataKataTextView.visibility = View.GONE
                        speakButton.visibility = View.VISIBLE
                        speakButton.setOnClickListener {
                            if (x != null) {
                                x.start()
                            }
                        }
                        indexArrayKata--
                    }
                    while(!attack && i>0) {
                        Log.e("WHILE", "While ke $i")
                        Log.e("ATTACK","Attack status $attack")
                        runOnUiThread {
                            val str:Int = i-1
                            if(attack && !wrongAnswer) {
                                speakButton.visibility = View.GONE
                                kataKataTextView.visibility = View.VISIBLE
                                kataKataTextView.setText("SERANG!")
                            }
                            else if(wrongAnswer){
                                speakButton.visibility = View.GONE
                                kataKataTextView.visibility = View.VISIBLE
                                countDownTextView.setText(str.toString())
                                kataKataTextView.setText("ketikanmu salah!")
                            }
                            else countDownTextView.setText(str.toString()) //ubah countdown
                            wrongAnswer = false
                        }
                        sleep(1000)
                        runOnUiThread {
                            kataKataTextView.visibility = View.GONE
                            speakButton.visibility = View.VISIBLE
                        }
                        i--
                    }
                    runOnUiThread {
                        if(attack && i>0){
                            score+=10
                            scoreTextView.setText(score.toString())
                            val animation = TranslateAnimation(
                                0.0f, 450.0f,
                                0.0f, 0.0f
                            )
                            animation.setDuration(1000)  // animation duration
                            animation.setRepeatCount(1)  // animation repeat count
                            animation.setRepeatMode(2)
                            dinoImageView.startAnimation(animation)

                            if(enemyHeart>=10){
                                if(level<3){
                                    closeEnemyHeart(enemyHeart)
                                    val animation = TranslateAnimation(
                                        0.0f, 0.0f,
                                        0.0f, 200.0f
                                    )
                                    animation.setDuration(2000)  // animation duration
                                    animation.setRepeatCount(0)  // animation repeat count
                                    animation.setRepeatMode(2)
                                    dinoImageView.startAnimation(animation)
                                    enemyImageView.startAnimation(animation)
                                    sleep(1000)
                                    kataKataTextView.visibility = View.VISIBLE
                                    nextGameButton.visibility = View.VISIBLE
                                    kataKataTextView.setText("Kamu Menang!")
                                    speakButton.visibility = View.GONE
                                    dinoImageView.visibility = View.INVISIBLE
                                    enemyImageView.visibility = View.INVISIBLE
                                    isGameRun = false
                                }else{
                                    isGameRun = false
                                    val animation = TranslateAnimation(
                                        0.0f, 0.0f,
                                        0.0f, 200.0f
                                    )
                                    animation.setDuration(2000)  // animation duration
                                    animation.setRepeatCount(0)  // animation repeat count
                                    animation.setRepeatMode(2)
                                    dinoImageView.startAnimation(animation)
                                    enemyImageView.startAnimation(animation)
                                    sleep(1000)
                                    dinoImageView.visibility = View.INVISIBLE
                                    enemyImageView.visibility = View.INVISIBLE
                                    kataKataTextView.visibility = View.VISIBLE
                                    kataKataTextView.setText("Level 2 Telah Selesai!")
                                    level2Button.visibility = View.VISIBLE
                                    speakButton.visibility = View.GONE
                                    enemyImageView.visibility = View.GONE
                                    if(scoreLast.toInt() < score+300){
                                        scoreLast = score.toString()
                                        putScore()
                                    }
                                    setLevel(3)
                                }
                            }else{
                                closeEnemyHeart(enemyHeart)
                                speakButton.visibility = View.GONE
                                kataKataTextView.visibility = View.VISIBLE
                                kataKataTextView.setText("Serang!")
                            }


                            Log.e("ATTACK","Attack status di luar while $attack")
                            enemyHeart++
                        }
                        else {
                            life--
//                            countDownTextView.setText("Aww!")
                            closePlayerHeart(playerHeart)
                            playerHeart++
                            val animation = TranslateAnimation(
                                0.0f, -150.0f,
                                0.0f, 0.0f
                            )
                            animation.setDuration(1000)  // animation duration
                            animation.setRepeatCount(1)  // animation repeat count
                            animation.setRepeatMode(2)

                            enemyImageView.startAnimation(animation)
                        }
                    }
                    sleep(1000)
                    runOnUiThread {
                        if(!attack){
                            val animation = TranslateAnimation(
                                0.0f, 0.0f,
                                0.0f, 150.0f
                            )
                            animation.setDuration(800)  // animation duration
                            animation.setRepeatCount(0)  // animation repeat count
                            animation.setRepeatMode(1)
                            scratchImageView.visibility = View.VISIBLE
                            scratchImageView.startAnimation(animation)
                        }else{
                            scratchMusuhImageView.visibility = View.VISIBLE
                            val animation2 = TranslateAnimation(
                                0.0f, 30.0f,
                                0.0f, 0.0f
                            )
                            animation2.setDuration(150)  // animation duration
                            animation2.setRepeatCount(1)  // animation repeat count
                            animation2.setRepeatMode(2)
                            enemyImageView.startAnimation(animation2)
                        }
                    }
                    sleep(1100)
                    attack = false
                    Log.e("I", "I Sekarang $i")

                    if(life == 1){
                        runOnUiThread {
                            floodbgImageView.visibility = View.VISIBLE
                        }
                    }

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
                            enemyImageView.startAnimation(animation)

                            speakButton.visibility = View.GONE
//                            score yang baru selalu dari 0 + skor yang baru didaatnya
                            if(scoreLast < (300+score).toString()){
                                Log.e("SCOREPUT","Masuk if")
                                scoreLast = (300+score).toString()
                                putScore()
                            }
//                            countDownTextView.setText("Kamu kalah!")
                            kataKataTextView.visibility = View.VISIBLE
                            kataKataTextView.setText("Kamu gagal!")
                            scratchImageView.visibility = View.INVISIBLE
                        }
                        sleep(2000)
                        runOnUiThread {
                            kataKataTextView.visibility = View.INVISIBLE
                            tryAgainButton.visibility = View.VISIBLE
                            backHomeButton.visibility = View.VISIBLE
                            dinoImageView.visibility = View.INVISIBLE
                            enemyImageView.visibility = View.INVISIBLE
                            scratchImageView.visibility = View.INVISIBLE
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
        intent.putExtra("nama", nama)
        intent.putExtra("username", username)
        intent.putExtra("score", scoreLast)
        intent.putExtra("level", levelLast)
        startActivity(intent)
        finish()
    }

    fun setLevel(levelBaru: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:bacabaca.online")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServicePut::class.java)

        if(levelLast.toInt()<levelBaru){
            levelLast = levelBaru.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.updateLevel(username,levelBaru.toString())

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                    } else {

                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }
            }
        }
    }

    fun putScore(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:bacabaca.online")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServicePut::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.updateScore(username,scoreLast.toInt())

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

//                    val acceptedIssuers: Array<X509Certificate?>?
//                        get() = arrayOf()
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            /* Create an ssl socket factory with our all-trusting manager */
            val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}