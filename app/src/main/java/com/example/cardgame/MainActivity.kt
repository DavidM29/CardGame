package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var playBtn: Button
    lateinit var seeCardsBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        seeCardsBtn.setOnClickListener {
            startActivity(Intent(this, CardsActivity::class.java))
        }
    }

    private fun init(){
        playBtn = findViewById(R.id.playBtn)
        seeCardsBtn = findViewById(R.id.seeCardsBtn)
    }
}