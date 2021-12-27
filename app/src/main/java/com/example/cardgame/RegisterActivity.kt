package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var emailET : TextView
    lateinit var passwordET : TextView
    lateinit var submitBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        submitBtn.setOnClickListener {
            var email = emailET.text.toString()
            var password = passwordET.text.toString()

            if (email.isEmpty()){
                Toast.makeText(this, "Please Specify Email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                Toast.makeText(this, "Please Specify Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { a ->
                    if (a.isSuccessful){
                        emailET.text = ""
                        passwordET.text = ""
                        startActivity(Intent(this, ChooseCardsActivity::class.java))
                    }
                }
        }
    }

    private fun init(){
        emailET = findViewById(R.id.regEmailET)
        passwordET = findViewById(R.id.regPasswordET)
        submitBtn = findViewById(R.id.regSubmitBTN)
    }
}