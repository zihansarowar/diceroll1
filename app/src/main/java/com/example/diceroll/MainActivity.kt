package com.example.diceroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object{
        const val diceKey = "diceKey"
        const val scoreKey ="scoreKey"
        const val rollButtonKey = "rollButtonKey"
    }
    var value: Int =0
    var diceValue : Int = 0
    var buttonState : Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        if(resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main)
            Log.d("portrait","Portrait mode on")
        }
        else{
            setContentView(R.layout.landscape)
            Log.d("landscape","landscape mode on")
        }

        val valueText : TextView = findViewById(R.id.score)
        val addButton : Button = findViewById(R.id.additionButton)
        val substractButton : Button = findViewById(R.id.substractButton)
        val resetButton : Button = findViewById(R.id.resetButton)
        val rollButton : Button = findViewById(R.id.rollButton)
        val diceImage : ImageView = findViewById(R.id.diceImageView)

        rollButton.setOnClickListener(){
            if(buttonState==true){
                diceValue = rollDice()
                when(diceValue){
                    1->diceImage.setImageResource(R.drawable.dice_1)
                    2->diceImage.setImageResource(R.drawable.dice_2)
                    3->diceImage.setImageResource(R.drawable.dice_3)
                    4->diceImage.setImageResource(R.drawable.dice_4)
                    5->diceImage.setImageResource(R.drawable.dice_5)

                    else->diceImage.setImageResource(R.drawable.dice_6)

                }
                Log.d("Dice value","random number of dice value")
                buttonState= false
            }
        }

        resetButton.setOnClickListener(){
            value = 0
            diceValue = 0
            diceImage.setImageResource(R.drawable.dice_6)
            changeScore(valueText,value)
            Log.d("Reset","Reset Button applied")

        }

        addButton.setOnClickListener(){
            value= value+ diceValue
            changeScore(valueText,value)
            diceValue= 0
            buttonState = true
            Log.d("Addition","Addition Button applied")
        }

        substractButton.setOnClickListener(){
            value = maxOf(0,value - diceValue)
            changeScore(valueText, value)
            diceValue = 0
            buttonState = true
            Log.d("Substract","Substract Button applied")
        }


    }

    override fun onSaveInstanceState(outState : Bundle){
        super.onSaveInstanceState(outState)
        outState.putInt(scoreKey,value)
        outState.putInt(diceKey, diceValue)
        outState.putBoolean(rollButtonKey, buttonState)
        Log.i("Onsave", value.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle){
        super.onRestoreInstanceState(savedInstanceState)
        value= savedInstanceState.getInt(scoreKey)
        changeScore(findViewById(R.id.score),value)
        diceValue = savedInstanceState.getInt(diceKey)
        buttonState= savedInstanceState.getBoolean(rollButtonKey)

        Log.i("OnRestore", value.toString())
    }

    fun changeScore(viewValue: TextView, value: Int){
        if(value<20){
            viewValue.setTextColor(ContextCompat.getColor(this,R.color.black))
        }
        else if(value > 20){
            viewValue.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
        else{
            viewValue.setTextColor(ContextCompat.getColor(this,R.color.green))
        }
        viewValue.text = value.toString()
        Log.d("Score","Score updating")
    }

    fun rollDice():Int{
        return (1..6).random()
    }
}
