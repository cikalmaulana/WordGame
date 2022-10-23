package com.example.wordgames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Level2Activity: AppCompatActivity() {
    private fun initComponent(){}
    private fun initListener(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_2)

        initComponent()
        initListener()

        getSupportActionBar()?.hide()
    }
}