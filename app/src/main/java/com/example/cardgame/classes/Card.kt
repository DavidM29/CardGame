package com.example.cardgame.classes

import java.io.Serializable

data class Card(val name: String, val attack: Int, val health: Int, val defence: Int): Serializable {

    override fun equals(other: Any?): Boolean {
        if (other is Card){
            if (other.name == name){
                return true
            } else if (other.attack == attack && other.health == health && other.defence == defence){
                return true
            }
        }
        return false
    }

}
