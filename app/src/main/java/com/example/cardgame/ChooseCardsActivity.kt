package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.children
import com.example.cardgame.classes.Card
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChooseCardsActivity : AppCompatActivity() {

    lateinit var cardsList: ListView
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_cards)

        var cardArray: ArrayList<Card> = ArrayList<Card>()
        val context = this
        var list = mutableListOf<String>()
        cardsList = findViewById(R.id.cardsList)
        var db = FirebaseDatabase.getInstance().getReference("cards")

        db.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cards = snapshot.children

                for (c in cards){
                    list.add(c.child("Name").value.toString())

                    cardArray.add(
                        Card(
                            c.child("Name").value.toString(),
                            c.child("Attack").value.toString().toFloat(),
                            c.child("Health").value.toString().toFloat(),
                            c.child("Defence").value.toString().toFloat(),
                        )
                    )
                }

                adapter= ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
                cardsList.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        cardsList.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, CardInfoActivity::class.java)
            var selectedCard = cardArray.find { c -> c.name == adapter.getItem(position) }
            intent.putExtra("CardName", selectedCard!!.name)
            intent.putExtra("Attack", selectedCard!!.attack)
            intent.putExtra("Health", selectedCard!!.health)
            intent.putExtra("Defence", selectedCard!!.defence)
            startActivity(intent)
        }
    }
}