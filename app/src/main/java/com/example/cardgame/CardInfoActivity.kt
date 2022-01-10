package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.cardgame.classes.Card

class CardInfoActivity : AppCompatActivity() {

    private lateinit var cardNameTv: TextView
    private lateinit var attackValTv: TextView
    private lateinit var healthValTv: TextView
    private lateinit var defenceValTv: TextView
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)

        init()
    }

    private fun init(){
        cardNameTv = findViewById(R.id.cardInfoCardName)
        attackValTv = findViewById(R.id.attackVal)
        healthValTv = findViewById(R.id.healthVal)
        defenceValTv = findViewById(R.id.defenceVal)

        var card = intent.getSerializableExtra("SelectedCard") as Card

        cardNameTv.text = card.name
        attackValTv.text = card.attack.toString()
        healthValTv.text = card.health.toString()
        defenceValTv.text = card.defence.toString()
    }
}