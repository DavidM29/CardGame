package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WinnerActivity : AppCompatActivity() {
    lateinit var winnerTxt: TextView
    lateinit var playAgainBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        winnerTxt = findViewById(R.id.winnerText)
        playAgainBtn = findViewById(R.id.playAgainBtn)

        var winner = intent.extras!!.getInt("Winner")

        winnerTxt.text = "Congrats! Player $winner Won!"

        playAgainBtn.setOnClickListener {
            var intent = Intent(this, PlayActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}