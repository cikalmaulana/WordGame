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

    lateinit var sound: MediaPlayer

    private var life: Int = 3
    private var isGameRun: Boolean = true
    private var attack: Boolean = false
    private var wrongAnswer: Boolean = false
    private var enemyHeart: Int = 1
    private var i: Int = 16
    private var time: Int = 16
    private var level:Int = 1
    private var indexArrayKata: Int = 32
    private var score: Int = 0

    private var username: String = ""
    private var scoreLast: String = ""
    private var levelLast:String =""
    private var nama:String=""

    private var enemyHearts = mapOf<String,ImageView>()
    private var playerHearts = mapOf<String,ImageView>()

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
                        kataKataTextView.setText("Cegah Monster Membuang Sampah Sembarangan!")
                        countDownTextView.setText(k.toString())
                        Log.e("COUNTAWAL","${k.toString()}")
                    }
                    sleep(1000)
                    k--
                }
                runOnUiThread {
                    countDownTextView.setText("Mulai!")
                }
                sleep(1000)
                while(life>0 && isGameRun){
                    scratchImageView.visibility = View.INVISIBLE
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
                        val randomIndex = Random.nextInt(1,indexArrayKata)
                        val kata = arrKata.get(randomIndex)
                        Log.i("KATASAATINI","Kata Sekarang Adalah $kata")
                        arrKata.removeAt(randomIndex)
                        kataKataTextView.setText(kata)
                        kataKataTextView.visibility = View.GONE
                        speakButton.visibility = View.VISIBLE
                        speakButton.setOnClickListener {
                            //get text from edit text
                            val toSpeak = kata
                            if (toSpeak == ""){
                                //if there is no text in edit text
                                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                //if there is text in edit text
                                Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
                                mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
                            }
                        }
                        indexArrayKata--
                    }
                    while(!attack && i>0) {
                        Log.e("WHILE", "While ke $i")
                        Log.e("ATTACK","Attack status $attack")
                        runOnUiThread {
                            val str:Int = i-1
                            if(attack && !wrongAnswer) countDownTextView.setText("Serang!")
                            else if(wrongAnswer) countDownTextView.setText("Salah!")
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
                                    kataKataTextView.visibility = View.VISIBLE
                                    nextGameButton.visibility = View.VISIBLE
                                    kataKataTextView.setText("Kamu Menang!")
                                    speakButton.visibility = View.INVISIBLE
                                    dinoImageView.visibility = View.INVISIBLE
                                    enemyImageView.visibility = View.INVISIBLE
                                    isGameRun = false
                                }else{
                                    isGameRun = false
                                    dinoImageView.visibility = View.INVISIBLE
                                    enemyImageView.visibility = View.INVISIBLE
                                    kataKataTextView.visibility = View.VISIBLE
                                    kataKataTextView.setText("Level 2 Telah Selesai!")
                                    level2Button.visibility = View.VISIBLE
                                    speakButton.visibility = View.GONE
                                    enemyImageView.visibility = View.GONE
                                    scoreLast = score.toString()
                                    putScore()
                                }
                            }else{
                                closeEnemyHeart(enemyHeart)
                                countDownTextView.setText("Serang!")
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
    fun putScore(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:192.168.1.9")
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