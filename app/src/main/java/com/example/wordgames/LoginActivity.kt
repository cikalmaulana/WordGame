package com.example.wordgames

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wordgames.ui.dashboard.DashboardFragment
import com.example.wordgames.ui.home.HomeFragment
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

class LoginActivity: AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var loginTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var passwordTextView: TextView
    lateinit var wrongPassTextView:TextView

    lateinit var usernameEditText:EditText
    lateinit var passwordEditText: EditText

    private var correctPassword:Boolean = false

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        loginTextView = findViewById(R.id.loginTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        passwordTextView = findViewById(R.id.passTextView)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        wrongPassTextView = findViewById(R.id.wrongPassTextView)
    }

    private fun initListener(){
        val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
        loginButton.setTypeface(playfull)
        registerButton.setTypeface(playfull)
        usernameTextView.setTypeface(playfull)
        passwordTextView.setTypeface(playfull)
        wrongPassTextView.setTypeface(playfull)

        loginButton.setOnClickListener {
//            if(usernameEditText.getText() =="")
            checkLogin()
//            Log.e("CORRECT",correctPassword.toString())
//            if(correctPassword){
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        loginTextView.setTypeface(airfool)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponent()
        initListener()

        getSupportActionBar()?.hide()
    }

    override fun onBackPressed(){
        val intent = Intent(this, LandingPageActivity::class.java)
        // start your next activity
        startActivity(intent)
        finish()
    }

    fun goHome(nama:String,score:String,level:String){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("nama", nama)
        intent.putExtra("score", score);
        intent.putExtra("level", level);
        startActivity(intent)
        finish()
    }

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

                        goHome(nama,scoreUser,levelUser)
                    }

                    Log.d("Pretty Printed JSON :", prettyJson)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    fun checkLogin(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https:192.168.1.9")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServiceGet::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val username = usernameEditText.getText().toString()
            val password = passwordEditText.getText().toString()
            val response = service.getUser(username)
            Log.e("RESPONSE", response.message().toString())
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

                        // ID
                        val namaUser = jsonArray.getJSONObject(i).getString("nama")
                        Log.i("NAMA ", namaUser)

                        val passwordUser= jsonArray.getJSONObject(i).getString("password")
                        Log.i("PASSWORD ", password)
                        if(passwordUser != password){
                            Log.e("GAGAL", " User Gagal Login")
                            wrongPassword()
                        }else{
                            getScoreLevel(username, namaUser)
                        }

                    }
                    if (prettyJson[0]==null || prettyJson.toString().equals("{\"result\":[]}")) wrongUsername()
                    Log.d("Pretty Printed JSON :", prettyJson)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    fun wrongPassword(){
        wrongPassTextView.setText("Password Salah!")
        wrongPassTextView.visibility = View.VISIBLE
    }

    fun wrongUsername(){
        wrongPassTextView.setText("Username Salah!")
        wrongPassTextView.visibility = View.VISIBLE
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