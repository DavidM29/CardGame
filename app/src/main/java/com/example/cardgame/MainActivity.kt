package com.example.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var playerNumTV: TextView
    private lateinit var emailET: TextView
    private lateinit var passwordET: TextView
    private lateinit var logInBtn: Button
    private lateinit var logOutBtn: Button
    private lateinit var registerBtn: Button
    private var firstPlayerLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        logInBtn.setOnClickListener {
            if (firstPlayerLoggedIn) {
                //მეორე მოთამაშეც თუ გაივლის ავტორიზაციას გადავა სათამაშო აქთივითიზე
            } else {
                //პირველი მოთამაშე გადის ავტორიზაციას
                firstPlayerLoggedIn = true
                playerNumTV.text = "2"
                emailET.text = ""
                passwordET.text = ""
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun init(){
        playerNumTV = findViewById(R.id.playerNumTV)
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        logInBtn = findViewById(R.id.logInBTN)
        logOutBtn = findViewById(R.id.logOutBTN)
        registerBtn = findViewById(R.id.registerBTN)
    }
}