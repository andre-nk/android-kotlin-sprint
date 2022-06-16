package com.andrenk.tabletop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Dice (private val numSides: Int){
    fun roll(): Int{
        return (1 .. numSides).random()
    }
}

class MainActivity : AppCompatActivity() {
    fun rollDice(){
        val diceImage: ImageView = findViewById(R.id.imageView)
        val d6 = Dice(6)
        when(d6.roll()){
            1 -> diceImage.setImageResource(R.drawable.dice_1)
            2 -> diceImage.setImageResource(R.drawable.dice_2)
            3 -> diceImage.setImageResource(R.drawable.dice_3)
            4 -> diceImage.setImageResource(R.drawable.dice_4)
            5 -> diceImage.setImageResource(R.drawable.dice_5)
            6 -> diceImage.setImageResource(R.drawable.dice_6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollerButton: Button = findViewById(R.id.button)
        rollerButton.setOnClickListener {
            rollDice()
        }

        rollDice()
    }
}