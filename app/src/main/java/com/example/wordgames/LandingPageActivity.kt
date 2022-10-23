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
        lateinit var bacabacaTextView1: TextView
        lateinit var bacabacaTextView2: TextView
        lateinit var quotesTextView: TextView

        private fun initComponent(){
            loginButton = findViewById(R.id.loginButton)
            bacabacaTextView1 = findViewById(R.id.bacabacaTextView1)
            bacabacaTextView2 = findViewById(R.id.bacabacaTextView2)
            quotesTextView = findViewById(R.id.quotesTextView)
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
            bacabacaTextView1.setTypeface(airfool)
            bacabacaTextView2.setTypeface(airfool)
            quotesTextView.setTypeface(montserrat)
            loginButton.setTypeface(playfull)
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
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
    }