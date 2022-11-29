    package com.example.wordgames

    import android.content.Intent
    import android.graphics.Typeface
    import android.os.Bundle
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity


    class LandingPageActivity: AppCompatActivity() {

        private var pressedTime: Long =0
        lateinit var loginButton: Button
//        lateinit var bacabacaTextView1: TextView
        lateinit var bacabacaTextView2: TextView
//        lateinit var quotesTextView: TextView
        lateinit var mempersembahkan1TextView: TextView
        lateinit var mempersembahkan2TextView: TextView

        private fun initComponent(){
            loginButton = findViewById(R.id.loginButton)
            mempersembahkan1TextView = findViewById(R.id.mempersembahkan1TextView)
            mempersembahkan2TextView = findViewById(R.id.mempersembahkan2TextView)
//            bacabacaTextView1 = findViewById(R.id.bacabacaTextView1)
//            bacabacaTextView2 = findViewById(R.id.bacabacaTextView2)
//            quotesTextView = findViewById(R.id.quotesTextView)
        }

        private fun initListener(){
            loginButton.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                // start your next activity
                startActivity(intent)
                finish()
            }
            val airfool = Typeface.createFromAsset(assets, "font/Airfools.otf")
            val montserrat= Typeface.createFromAsset(assets, "font/Montserrat.ttf")
            val playfull= Typeface.createFromAsset(assets, "font/playfull.otf")
//            bacabacaTextView1.setTypeface(airfool)
//            bacabacaTextView2.setTypeface(airfool)
//            quotesTextView.setTypeface(montserrat)
            loginButton.setTypeface(playfull)
            mempersembahkan1TextView.setTypeface(playfull)
            mempersembahkan2TextView.setTypeface(playfull)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_landing_page)

            initComponent()
            initListener()

            getSupportActionBar()?.hide()
        }

        override fun onBackPressed(){
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Tekan kembali sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
    }