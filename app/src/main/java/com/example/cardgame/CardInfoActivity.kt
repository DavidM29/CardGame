package com.example.cardgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CardInfoActivity : AppCompatActivity() {

    lateinit var cardNameTv: TextView
    lateinit var attackValTv: TextView
    lateinit var healthValTv: TextView
    lateinit var defenceValTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)

        cardNameTv = findViewById(R.id.cardInfoCardName)
        attackValTv = findViewById(R.id.attackVal)
        healthValTv = findViewById(R.id.healthVal)
        defenceValTv = findViewById(R.id.defenceVal)

        cardNameTv.text = intent.extras!!.getString("CardName")
        attackValTv.text = intent.extras!!.getFloat("Attack").toString()
        healthValTv.text = intent.extras!!.getFloat("Health").toString()
        defenceValTv.text = intent.extras!!.getFloat("Defence").toString()
    }
}