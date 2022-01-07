package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CardInfoActivity : AppCompatActivity() {

    lateinit var cardNameTv: TextView
    lateinit var attackValTv: TextView
    lateinit var healthValTv: TextView
    lateinit var defenceValTv: TextView
    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)

        init()

        backBtn.setOnClickListener {
            intent = Intent(this, CardsActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun init(){
        cardNameTv = findViewById(R.id.cardInfoCardName)
        attackValTv = findViewById(R.id.attackVal)
        healthValTv = findViewById(R.id.healthVal)
        defenceValTv = findViewById(R.id.defenceVal)
        backBtn = findViewById(R.id.cardInfoBackBtn)

        cardNameTv.text = intent.extras!!.getString("CardName")
        attackValTv.text = intent.extras!!.getInt("Attack").toString()
        healthValTv.text = intent.extras!!.getInt("Health").toString()
        defenceValTv.text = intent.extras!!.getInt("Defence").toString()
    }
}