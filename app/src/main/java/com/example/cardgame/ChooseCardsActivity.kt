package com.example.cardgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChooseCardsActivity : AppCompatActivity() {

    lateinit var cardsList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_cards)

        var auth = FirebaseAuth.getInstance()
        cardsList = findViewById(R.id.cardsList)

        var db = FirebaseDatabase.getInstance().getReference("Card")

        var cardTable = db.child(auth.currentUser?.uid!!).child("Card").child("TestCard").get()//არ მუშაობს!!
    }
}