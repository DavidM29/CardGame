package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.cardgame.classes.Card
import com.google.firebase.database.FirebaseDatabase

class CreateCardActivity : AppCompatActivity() {

    private lateinit var inputCardName: TextView
    private lateinit var createCardBtn: Button
    private lateinit var plusAttack: Button
    private lateinit var plusHealth: Button
    private lateinit var plusDefence: Button
    private lateinit var minusAttack: Button
    private lateinit var minusHealth: Button
    private lateinit var minusDefence: Button
    private lateinit var attackCount: TextView
    private lateinit var healthCount: TextView
    private lateinit var defenceCount: TextView
    private lateinit var pointsCountTv: TextView
    private lateinit var cards: ArrayList<Card>
    private var pointsCount: Int = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        init()
        plusOnClickListeners()
        minusOnClickListeners()

        cards = intent.getSerializableExtra("Cards") as ArrayList<Card>

        val db = FirebaseDatabase.getInstance().getReference("cards")

        createCardBtn.setOnClickListener {
            if (pointsCount != 0){
                Toast.makeText(this, "Assign All Points!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputCardName.text.toString() == ""){
                Toast.makeText(this, "Input Name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var card = Card(inputCardName.text.toString(), attackCount.text.toString().toInt(), healthCount.text.toString().toInt(), defenceCount.text.toString().toInt())

            if (!cards.contains(card)) {
                db.child(card.name).setValue(card).addOnCompleteListener { c ->
                    if (c.isSuccessful) {
                        Toast.makeText(this, "Card Created Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, CardsActivity::class.java))
                    }
                }
            } else
                Toast.makeText(this, "This Card Already Exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        inputCardName = findViewById(R.id.inputCardName)
        createCardBtn = findViewById(R.id.createCardBtn)
        plusAttack = findViewById(R.id.plusAttack)
        plusHealth = findViewById(R.id.plusHealth)
        plusDefence = findViewById(R.id.plusDefence)
        minusAttack = findViewById(R.id.minusAttack)
        minusHealth = findViewById(R.id.minusHealth)
        minusDefence = findViewById(R.id.minusDefence)
        attackCount = findViewById(R.id.attackCount)
        healthCount = findViewById(R.id.healthCount)
        defenceCount = findViewById(R.id.defenceCount)
        pointsCountTv = findViewById(R.id.pointsCount)
    }

    private fun plusOnClickListeners(){

        plusAttack.setOnClickListener {
            if (pointsCount > 0 && attackCount.text.toString().toInt() < 20){
                var i = attackCount.text.toString().toInt()
                i++
                pointsCount--
                attackCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }
        }

        plusHealth.setOnClickListener {
            if (pointsCount > 0){
                var i = healthCount.text.toString().toInt()
                i++
                pointsCount--
                healthCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }

        }

        plusDefence.setOnClickListener {
            if (pointsCount > 0 && defenceCount.text.toString().toInt() < 5){
                var i = defenceCount.text.toString().toInt()
                i++
                pointsCount--
                defenceCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }
        }

    }

    private fun minusOnClickListeners(){

        minusAttack.setOnClickListener {
            if (attackCount.text.toString().toInt() > 10){
                var i = attackCount.text.toString().toInt()
                i--
                pointsCount++
                attackCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }
        }

        minusHealth.setOnClickListener {
            if (healthCount.text.toString().toInt() > 50){
                var i = healthCount.text.toString().toInt()
                i--
                pointsCount++
                healthCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }
        }

        minusDefence.setOnClickListener {
            if (defenceCount.text.toString().toInt() > 1){
                var i = defenceCount.text.toString().toInt()
                i--
                pointsCount++
                defenceCount.text = i.toString()
                pointsCountTv.text = pointsCount.toString()
            }
        }

    }
}