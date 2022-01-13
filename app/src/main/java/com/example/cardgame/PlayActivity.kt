package com.example.cardgame

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
    private lateinit var player1WeakAtkBtn: Button
    private lateinit var player2WeakAtkBtn: Button
    private lateinit var player1DefenceBtn: Button
    private lateinit var player2DefenceBtn: Button
    private lateinit var player1HealthBar: ProgressBar
    private lateinit var player2HealthBar: ProgressBar
    private lateinit var player1ManaBar: ProgressBar
    private lateinit var player2ManaBar: ProgressBar

    private lateinit var player1CardName: TextView
    private lateinit var player2CardName: TextView

    private var player1Health = 0
    private var player2Health = 0

    private var player1Mana = 100
    private var player2Mana = 100

    private var maxMana = 100
    private var manaRegenPerTurn = 10
    private var attackManaCost = 50
    private var defendManaCost = 5
    private var weakAttack = 3

    private var defend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play)

        init()
        getCards()
        setOnClickListeners()

    }

    ///
    private fun init(){
        player1AttackBtn = findViewById(R.id.player1AttackBtn)
        player2AttackBtn = findViewById(R.id.player2AttackBtn)
        player1WeakAtkBtn = findViewById(R.id.player1WeakAttack)
        player2WeakAtkBtn = findViewById(R.id.player2WeakAttack)
        player1DefenceBtn = findViewById(R.id.player1DefendBtn)
        player2DefenceBtn = findViewById(R.id.player2DefendBtn)
        player1HealthBar = findViewById(R.id.player1Health)
        player2HealthBar = findViewById(R.id.player2Health)
        player1ManaBar = findViewById(R.id.player1Mana)
        player2ManaBar = findViewById(R.id.player2Mana)
        player1CardName = findViewById(R.id.player1CardName)
        player2CardName = findViewById(R.id.player2CardName)
        cardsArray = ArrayList()

        player1AttackBtn.setBackgroundColor(resources.getColor(R.color.grey))
        player1WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.grey))
        player1DefenceBtn.setBackgroundColor(resources.getColor(R.color.grey))
        player2AttackBtn.setBackgroundColor(resources.getColor(R.color.grey))
        player2WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.grey))
        player2DefenceBtn.setBackgroundColor(resources.getColor(R.color.grey))
    }

    ///
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

    ///
    private fun changePlayer1Enabled(isEnabled: Boolean){
        player1AttackBtn.isEnabled = isEnabled
        player1WeakAtkBtn.isEnabled = isEnabled
        player1DefenceBtn.isEnabled = isEnabled

        if (isEnabled) {
            player1AttackBtn.setBackgroundColor(resources.getColor(R.color.green))
            player1WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.green))
            player1DefenceBtn.setBackgroundColor(resources.getColor(R.color.green))
        }else{
            player1AttackBtn.setBackgroundColor(resources.getColor(R.color.grey))
            player1WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.grey))
            player1DefenceBtn.setBackgroundColor(resources.getColor(R.color.grey))
        }
    }

    ///
    private fun changePlayer2Enabled(isEnabled: Boolean){
        player2AttackBtn.isEnabled = isEnabled
        player2WeakAtkBtn.isEnabled = isEnabled
        player2DefenceBtn.isEnabled = isEnabled

        if (isEnabled) {
            player2AttackBtn.setBackgroundColor(resources.getColor(R.color.green))
            player2WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.green))
            player2DefenceBtn.setBackgroundColor(resources.getColor(R.color.green))
        }else{
            player2AttackBtn.setBackgroundColor(resources.getColor(R.color.grey))
            player2WeakAtkBtn.setBackgroundColor(resources.getColor(R.color.grey))
            player2DefenceBtn.setBackgroundColor(resources.getColor(R.color.grey))
        }

    }

    ///
    private fun setCardNames(){
        player1CardName.text = player1Card.name
        player2CardName.text = player2Card.name
    }

    ///
    private fun setOnClickListeners(){

        //Player 1//////////////////////////////////////////////////////////////////////////////////
        player1AttackBtn.setOnClickListener {
            if (player1Mana >= attackManaCost) {
                player1Mana -= attackManaCost
                player1Attack(player1Card.attack)
            }else
                Toast.makeText(this, "You Don't Have Enough Mana", Toast.LENGTH_SHORT).show()
        }

        player1WeakAtkBtn.setOnClickListener {
            player1Attack(weakAttack)
        }

        player1DefenceBtn.setOnClickListener {
            if (player1Mana >= defendManaCost) {
                player1Mana -= defendManaCost
                defend(1)
            }else
                Toast.makeText(this, "You Don't Have Enough Mana", Toast.LENGTH_SHORT).show()
        }

        //Player 2//////////////////////////////////////////////////////////////////////////////////
        player2AttackBtn.setOnClickListener {
            if (player2Mana >= attackManaCost) {
                player2Mana -= attackManaCost
                player2Attack(player2Card.attack)
            }else
                Toast.makeText(this, "You Don't Have Enough Mana", Toast.LENGTH_SHORT).show()
        }

        player2WeakAtkBtn.setOnClickListener {
            player2Attack(weakAttack)
        }

        player2DefenceBtn.setOnClickListener {
            if (player2Mana >= defendManaCost) {
                player2Mana -= defendManaCost
                defend(2)
            }else
                Toast.makeText(this, "You Don't Have Enough Mana", Toast.LENGTH_SHORT).show()
        }
    }

    ///
    private fun getDmg(attack: Int, defence: Int) :Int{
        var dmg = if (defend){
            (attack - defence)
        }else
            attack

        if (dmg < 0)
            dmg = 0

        return dmg
    }

    ///
    private fun checkWin(activePlayer: Int, defHealth: Int){
        if (defHealth <= 0){
            intent = Intent(this, WinnerActivity::class.java)
            intent.putExtra("Winner", activePlayer)
            finish()
            startActivity(intent)
        }
    }

    ///
    private fun defend(activePlayer: Int){
        defend = true

        updateMana(activePlayer)

        changePlayer1Enabled(activePlayer != 1)
        changePlayer2Enabled(activePlayer != 2)
    }

    ///
    private fun player1Attack(attack: Int){
        var dmg = getDmg(attack, player2Card.defence)

        player2Health -= dmg
        Toast.makeText(this, "Player 1 Dealt $dmg Damage!", Toast.LENGTH_SHORT).show()

        checkWin(1, player2Health)

        var health = (player2Health.toDouble() / player2Card.health.toDouble()) * 100
        player2HealthBar.progress = health.roundToInt()

        updateMana(1)

        changePlayer1Enabled(false)
        changePlayer2Enabled(true)

        defend = false
    }

    ///
    private fun player2Attack(attack: Int){
        var dmg = getDmg(attack, player1Card.defence)

        player1Health -= dmg
        Toast.makeText(this, "Player 2 Dealt $dmg Damage!", Toast.LENGTH_SHORT).show()

        checkWin(2, player1Health)

        val health = (player1Health.toDouble() / player1Card.health.toDouble()) * 100
        player1HealthBar.progress = health.roundToInt()

        updateMana(2)

        changePlayer1Enabled(true)
        changePlayer2Enabled(false)

        defend = false
    }

    ///
    private fun updateMana(activePlayer: Int){
        if (activePlayer == 1){
            player1Mana += manaRegenPerTurn
            val mana = (player1Mana.toDouble() / maxMana.toDouble()) * 100
            player1ManaBar.progress = mana.roundToInt()
        }else{
            player2Mana += manaRegenPerTurn
            val mana = (player2Mana.toDouble() / maxMana.toDouble()) * 100
            player2ManaBar.progress = mana.roundToInt()
        }
    }
}