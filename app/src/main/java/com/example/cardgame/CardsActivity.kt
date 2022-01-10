package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.cardgame.classes.Card
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CardsActivity : AppCompatActivity() {

    private lateinit var cardsList: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var cardArray: ArrayList<Card>
    private lateinit var createYourFighterBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        init()

        getCards()

        cardsList.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, CardInfoActivity::class.java)
            val selectedCard = cardArray.find { c -> c.name == adapter.getItem(position) }
            intent.putExtra("SelectedCard", selectedCard)
            startActivity(intent)
        }

        createYourFighterBtn.setOnClickListener {
            var intent = Intent(this, CreateCardActivity::class.java)
            intent.putExtra("Cards", cardArray)
            startActivity(intent)
        }

    }

    //Initialization
    private fun init(){
        cardsList = findViewById(R.id.cardsList)
        cardArray = ArrayList()
        createYourFighterBtn = findViewById(R.id.createYourFighterBtn)
    }

    //Getting Cards
    private fun getCards(){
        val list = mutableListOf<String>()
        val db = FirebaseDatabase.getInstance().getReference("cards")

        val context = this

        db.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cards = snapshot.children

                for (c in cards){
                    list.add(c.child("name").value.toString())

                    cardArray.add(
                        Card(
                            c.child("name").value.toString(),
                            c.child("attack").value.toString().toInt(),
                            c.child("health").value.toString().toInt(),
                            c.child("defence").value.toString().toInt(),
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

    }
}