package com.example.wordgames

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
    private var username: String = ""
    private var score: String = ""
    private var level:String = ""
    private var nama:String = ""

    private fun initComponent(){

    }

    private fun initListener(){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        nama = intent.getStringExtra("nama").toString()
        username = intent.getStringExtra("username").toString()
        score = intent.getStringExtra("score").toString()
        level = intent.getStringExtra("level").toString()

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

//    Nanti di looping nya, masukin score + username ke masing masing texteview
//    TextView nya pake map / arraylist aja kaya yg playerheart
//    jadi tinggal di loop, masuk masukin satu sau di looping itu 10 buah

    fun getScoreLevel(username:String, nama:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:192.168.1.9")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServiceGet::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getScore(username)

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

                    for (i in 0 until jsonArray.length()) {

                        //score
                        val scoreUser = jsonArray.getJSONObject(i).getString("score")
                        Log.i("SCORE ", scoreUser)

                        val levelUser = jsonArray.getJSONObject(i).getString("level")
                        Log.i("LEVEL ", levelUser)

                        val username = jsonArray.getJSONObject(i).getString("username")
                        Log.i("USERNAME ", username)

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