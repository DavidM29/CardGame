package com.example.cardgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.cardgame.classes.Card
import com.google.firebase.database.FirebaseDatabase

class CreateCardActivity : AppCompatActivity() {

    lateinit var createCardBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        init()

        val db = FirebaseDatabase.getInstance().getReference("cards")

        val newCard = Card("Card_2", 10, 10, 5)

        createCardBtn.setOnClickListener {
            db.child(newCard.name).setValue(newCard).addOnCompleteListener {
                c ->
                if (c.isSuccessful){
                    Toast.makeText(this,"Card Created Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun init(){
        createCardBtn = findViewById(R.id.createCardbtn)
    }
}