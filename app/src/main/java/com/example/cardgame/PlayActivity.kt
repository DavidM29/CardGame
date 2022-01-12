package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.cardgame.classes.Card
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.function.DoubleConsumer
import kotlin.math.round
import kotlin.math.roundToInt

class PlayActivity : AppCompatActivity() {

    private lateinit var cardsArray: ArrayList<Card>
    private lateinit var player1Card: Card
    private lateinit var player2Card: Card

    private lateinit var player1AttackBtn: Button
    private lateinit var player2AttackBtn: Button
    private lateinit var player1DefenceBtn: Button
    private lateinit var player2DefenceBtn: Button
    private lateinit var player1HealthBar: ProgressBar
    private lateinit var player2HealthBar: ProgressBar

    private lateinit var player1CardName: TextView
    private lateinit var player2CardName: TextView

    private var player1Health = 0
    private var player2Health = 0

    private var defend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play)

        init()
        getCards()
        setOnClickListeners()

    }

    private fun init(){
        player1AttackBtn = findViewById(R.id.player1AttackBtn)
        player2AttackBtn = findViewById(R.id.player2AttackBtn)
        player1DefenceBtn = findViewById(R.id.player1DefendBtn)
        player2DefenceBtn = findViewById(R.id.player2DefendBtn)
        player1HealthBar = findViewById(R.id.player1Health)
        player2HealthBar = findViewById(R.id.player2Health)
        player1CardName = findViewById(R.id.player1CardName)
        player2CardName = findViewById(R.id.player2CardName)
        cardsArray = ArrayList()
    }

    private fun getCards(){
        val db = FirebaseDatabase.getInstance().getReference("cards")

        db.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cards = snapshot.children

                for (c in cards){
                    var card = Card(
                        c.child("name").value.toString(),
                        c.child("attack").value.toString().toInt(),
                        c.child("health").value.toString().toInt(),
                        c.child("defence").value.toString().toInt())

                    if (!cardsArray.contains(card))
                        cardsArray.add(card)
                }

                //Giving Players Random Cards
                var tempCardsArray = cardsArray
                player1Card = tempCardsArray.random()
                player1Health = player1Card.health

                tempCardsArray.remove(player1Card)
                player2Card = tempCardsArray.random()
                player2Health = player2Card.health

                setCardNames()
                changePlayer1Enabled(true)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //This Approach Sometimes Does Not Work
        /*
        db.get().addOnSuccessListener {
            val cards = it.children

            for (c in cards){
                cardsArray.add(
                    Card(
                        c.child("name").value.toString(),
                        c.child("attack").value.toString().toInt(),
                        c.child("health").value.toString().toInt(),
                        c.child("defence").value.toString().toInt()
                    )
                )
            }

            //Giving Players Random Cards
            val tempCardsArray = cardsArray
            player1Card = tempCardsArray.random()
            player1Health = player1Card.health

            tempCardsArray.remove(player1Card)
            player2Card = tempCardsArray.random()
            player2Health = player2Card.health

            setCardNames()
            changePlayer1Enabled(true)
        }
        */
    }

    private fun changePlayer1Enabled(isEnabled: Boolean){
        player1AttackBtn.isEnabled = isEnabled
        player1DefenceBtn.isEnabled = isEnabled
    }

    private fun changePlayer2Enabled(isEnabled: Boolean){
        player2AttackBtn.isEnabled = isEnabled
        player2DefenceBtn.isEnabled = isEnabled
    }

    private fun setCardNames(){
        player1CardName.text = player1Card.name
        player2CardName.text = player2Card.name
    }

    private fun setOnClickListeners(){
        player1AttackBtn.setOnClickListener {
            val dmg = if (defend){
                (player1Card.attack - player2Card.defence)
            }else
                player1Card.attack

            player2Health -= dmg
            Toast.makeText(this, "Player 1 Dealt $dmg Damage!", Toast.LENGTH_SHORT).show()

            if (player2Health <= 0){
                intent = Intent(this, WinnerActivity::class.java)
                intent.putExtra("Winner", 1)
                finish()
                startActivity(intent)
            }

            var progress = (player2Health.toDouble() / player2Card.health.toDouble()) * 100
            player2HealthBar.progress = progress.roundToInt()
            defend = false

            changePlayer1Enabled(false)
            changePlayer2Enabled(true)
        }

        player1DefenceBtn.setOnClickListener {
            defend = true

            changePlayer1Enabled(false)
            changePlayer2Enabled(true)
        }

        player2AttackBtn.setOnClickListener {
            val dmg = if (defend){
                (player2Card.attack - player2Card.defence)
            }else
                player2Card.attack

            Toast.makeText(this, "Player 2 Dealt $dmg Damage!", Toast.LENGTH_SHORT).show()
            player1Health -= dmg

            if (player1Health <= 0){
                intent = Intent(this, WinnerActivity::class.java)
                intent.putExtra("Winner", 2)
                finish()
                startActivity(intent)
            }

            val progress = (player1Health.toDouble() / player1Card.health.toDouble()) * 100
            player1HealthBar.progress = progress.roundToInt()
            defend = false

            changePlayer1Enabled(true)
            changePlayer2Enabled(false)
        }

        player2DefenceBtn.setOnClickListener {
            defend = true

            changePlayer1Enabled(true)
            changePlayer2Enabled(false)
        }
    }
}