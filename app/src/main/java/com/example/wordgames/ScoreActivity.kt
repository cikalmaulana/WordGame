package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class ScoreActivity: AppCompatActivity() {

    lateinit var scoreTextView1: TextView
    lateinit var scoreTextView2: TextView
    lateinit var scoreTextView3: TextView
    lateinit var scoreTextView4: TextView
    lateinit var scoreTextView5: TextView
    lateinit var scoreTextView6: TextView
    lateinit var scoreTextView7: TextView
    lateinit var scoreTextView8: TextView
    lateinit var scoreTextView9: TextView
    lateinit var scoreTextView10: TextView

    lateinit var usernameTextView1: TextView
    lateinit var usernameTextView2: TextView
    lateinit var usernameTextView3: TextView
    lateinit var usernameTextView4: TextView
    lateinit var usernameTextView5: TextView
    lateinit var usernameTextView6: TextView
    lateinit var usernameTextView7: TextView
    lateinit var usernameTextView8: TextView
    lateinit var usernameTextView9: TextView
    lateinit var usernameTextView10: TextView
    lateinit var topScoreTextView:TextView

    private var usernameTextView = mapOf<String, TextView>()
    private var scoreTextView = mapOf<String, TextView>()

    private var username: String = ""
    private var score: String = ""
    private var level:String = ""
    private var nama:String = ""

    private fun initComponent(){
        topScoreTextView = findViewById(R.id.topScoreTextView)

        usernameTextView1 = findViewById(R.id.usernameTextView1)
        usernameTextView2 = findViewById(R.id.usernameTextView2)
        usernameTextView3 = findViewById(R.id.usernameTextView3)
        usernameTextView4 = findViewById(R.id.usernameTextView4)
        usernameTextView5 = findViewById(R.id.usernameTextView5)
        usernameTextView6 = findViewById(R.id.usernameTextView6)
        usernameTextView7 = findViewById(R.id.usernameTextView7)
        usernameTextView8 = findViewById(R.id.usernameTextView8)
        usernameTextView9 = findViewById(R.id.usernameTextView9)
        usernameTextView10 = findViewById(R.id.usernameTextView10)

        scoreTextView1 = findViewById(R.id.scoreTextView1)
        scoreTextView2 = findViewById(R.id.scoreTextView2)
        scoreTextView3 = findViewById(R.id.scoreTextView3)
        scoreTextView4 = findViewById(R.id.scoreTextView4)
        scoreTextView5 = findViewById(R.id.scoreTextView5)
        scoreTextView6 = findViewById(R.id.scoreTextView6)
        scoreTextView7 = findViewById(R.id.scoreTextView7)
        scoreTextView8 = findViewById(R.id.scoreTextView8)
        scoreTextView9 = findViewById(R.id.scoreTextView9)
        scoreTextView10 = findViewById(R.id.scoreTextView10)
    }

    private fun initListener(){
        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        topScoreTextView.setTypeface(airfool)
        usernameTextView = mapOf(
            "usernameTextView1" to usernameTextView1,
            "usernameTextView2" to usernameTextView2,
            "usernameTextView3" to usernameTextView3,
            "usernameTextView4" to usernameTextView4,
            "usernameTextView5" to usernameTextView5,
            "usernameTextView6" to usernameTextView6,
            "usernameTextView7" to usernameTextView7,
            "usernameTextView8" to usernameTextView8,
            "usernameTextView9" to usernameTextView9,
            "usernameTextView10" to usernameTextView10,
        )

        scoreTextView = mapOf(
            "scoreTextView1" to scoreTextView1,
            "scoreTextView2" to scoreTextView2,
            "scoreTextView3" to scoreTextView3,
            "scoreTextView4" to scoreTextView4,
            "scoreTextView5" to scoreTextView5,
            "scoreTextView6" to scoreTextView6,
            "scoreTextView7" to scoreTextView7,
            "scoreTextView8" to scoreTextView8,
            "scoreTextView9" to scoreTextView9,
            "scoreTextView10" to scoreTextView10,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        getSupportActionBar()?.hide()

        nama = intent.getStringExtra("nama").toString()
        username = intent.getStringExtra("username").toString()
        score = intent.getStringExtra("score").toString()
        level = intent.getStringExtra("level").toString()

        initComponent()
        initListener()

        getScore()
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

//    Nanti di looping nya, masukin score + username ke masing masing texteview
//    TextView nya pake map / arraylist aja kaya yg playerheart
//    jadi tinggal di loop, masuk masukin satu sau di looping itu 10 buah

    fun getScore(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:192.168.1.9")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServiceGet::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAllScore()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setLenient().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    val jsonObject = JSONTokener(prettyJson).nextValue() as JSONObject

                    val jsonArray = jsonObject.getJSONArray("result")

                    var index = 1

                    for (i in 0 until jsonArray.length()) {

                        val usernameUser = jsonArray.getJSONObject(i).getString("username")
                        Log.i("USERNAMEUSER ", usernameUser)

                        val scoreUser = jsonArray.getJSONObject(i).getString("score")
                        Log.i("SCORE ", scoreUser)

                        val levelUser = jsonArray.getJSONObject(i).getString("level")
                        Log.i("LEVEL ", levelUser)

                        Log.e("INDEX", index.toString())
                        usernameTextView.get("usernameTextView$index")?.setText("$index. $usernameUser")
                        scoreTextView.get("scoreTextView$index")?.setText("$scoreUser pts")
                        index++
                    }

                    Log.d("Pretty Printed JSON :", prettyJson)
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