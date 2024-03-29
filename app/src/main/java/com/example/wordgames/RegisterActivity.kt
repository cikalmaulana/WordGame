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
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.json.JSONTokener
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class RegisterActivity: AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var registerTextView: TextView
    lateinit var asalSekolahEditText: EditText
    lateinit var namaEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var usernameEditText: EditText
    lateinit var alamatEditText: EditText
    lateinit var kelasEditText: EditText
    lateinit var warningTextView:TextView

    lateinit var namaTextView: TextView
    lateinit var alamatTextView: TextView
    lateinit var asalSekolahTextView: TextView
    lateinit var kelasTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var passwordTextView: TextView
    lateinit var atauTextView:TextView

    private var isUsernameExist: Boolean = true

    private fun initComponent(){
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        registerTextView = findViewById(R.id.registerTextView)
        asalSekolahEditText = findViewById(R.id.asalSekolahEditText)
        namaEditText = findViewById(R.id.namaEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        alamatEditText = findViewById(R.id.alamatEditText)
        kelasEditText = findViewById(R.id.kelasEditText)
        warningTextView = findViewById(R.id.warningTextView)

        namaTextView = findViewById(R.id.namaTextView)
        alamatTextView = findViewById(R.id.alamatTextView)
        asalSekolahTextView = findViewById(R.id.asalSekolahTextView)
        kelasTextView = findViewById(R.id.kelasTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        passwordTextView = findViewById(R.id.passwordTextView)
        atauTextView = findViewById(R.id.atauTextView)
    }

    private fun initListener(){
        val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
        val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
        registerTextView.setTypeface(airfool)
        warningTextView.setTypeface(playfull)
        loginButton.setTypeface(playfull)
        registerButton.setTypeface(playfull)
        namaTextView.setTypeface(playfull)
        alamatTextView.setTypeface(playfull)
        asalSekolahTextView.setTypeface(playfull)
        kelasTextView.setTypeface(playfull)
        usernameTextView.setTypeface(playfull)
        passwordTextView.setTypeface(playfull)
        atauTextView.setTypeface(playfull)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            if(asalSekolahEditText.getText().toString().isEmpty() ||
                namaEditText.getText().toString().isEmpty() ||
                passwordEditText.getText().toString().isEmpty() ||
                usernameEditText.getText().toString().isEmpty() ||
                alamatEditText.getText().toString().isEmpty() ||
                kelasEditText.getText().toString().isEmpty()){

                    warningTextView.visibility = View.VISIBLE
                    warningTextView.setText("Data Harus Lengkap!")

            }else{
                cekUsername()
                Thread.sleep(1000)
                Log.e("USERNAMEEXISTATAS", isUsernameExist.toString())
//                if(isUsernameExist){
//                    warningTextView.visibility = View.VISIBLE
//                    warningTextView.setText("Username sudah terdaftar!")
//                }else{
                    registerUser(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        namaEditText.getText().toString(),
                        alamatEditText.getText().toString(),
                        asalSekolahEditText.getText().toString(),
                        kelasEditText.getText().toString()
                    )
//                }
//                warningTextView.visibility = View.INVISIBLE

            }

        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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

    fun cekUsername(){
        var res = true
        val retrofit = Retrofit.Builder()
            .baseUrl("https:bacabaca.online")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServiceGet::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val username = usernameEditText.getText().toString()
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

                    if (prettyJson[0]==null || prettyJson.toString().equals("{\"result\":[]}")) isUsernameExist = false
                    else isUsernameExist = true
                    Log.e("USERNAMETERDAFTARATAS", isUsernameExist.toString())
                    Log.d("USERNAMETERDAFTAR", prettyJson)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
//    Register awal
    fun registerScore(username:String){
// Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bacabaca.online")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create HashMap with fields
        val params = HashMap<String?, String?>()
        params["score"] = "0"
        params["level"] = "1"
        params["username"] = username

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.createScore(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("SUCCESS", "${response.body()}")
                    // Convert raw JSON to pretty JSON using GSON library


//                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
    fun registerUser(username: String, pass:String, nama:String, alamat:String,nama_sekolah:String, kelas:String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bacabaca.online")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create HashMap with fields
        val params = HashMap<String?, String?>()
        params["username"] = username
        params["pass"] = pass
        params["nama"] = nama
        params["alamat"] = alamat
        params["nama_sekolah"] = nama_sekolah
        params["kelas"] = kelas

        CoroutineScope(Dispatchers.IO).launch {
            Log.e("USERNAMEEXISTBAWAH", isUsernameExist.toString())
            if(!isUsernameExist){
                // Do the POST request and get response
                val response = service.createEmployee(params)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        registerScore(username)
                        passwordEditText.setText("")
                        namaEditText.setText("")
                        alamatEditText.setText("")
                        asalSekolahEditText.setText("")
                        kelasEditText.setText("")
                        usernameEditText.setText("")
                        warningTextView.visibility = View.VISIBLE
                        warningTextView.setText("Registrasi Berhasil, Silahkan Login")
                        Log.e("SUCCESS", "${response.body()}")
                        // Convert raw JSON to pretty JSON using GSON library


    //                    Log.d("Pretty Printed JSON :", prettyJson)

                    } else {

                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }
            }else{
                runOnUiThread {
                    warningTextView.visibility = View.VISIBLE
                    warningTextView.setText("Username sudah terdaftar!")
                }
            }
        }
    }

}