package com.example.cardgame.classes

data class Player(val emailArg: String, val passwordArg: String, val cardsArg: ArrayList<String>?){

    var email = emailArg
    var password = passwordArg
    var cards = cardsArg

}
