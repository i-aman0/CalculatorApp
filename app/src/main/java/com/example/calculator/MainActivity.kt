package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView?=null
    var lastNumeric : Boolean=false
    var lastDot : Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false
    }

    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    // this function makes sure that on clicking an operator button if last character is number
    // and an operator is not added then add the operator corresponding to the button clicked
    // it allows to add only one operator and one '-' at the beginning
    fun onOperator(view: View){
        // if tvInput exists i.e. is not null, then execute the enclosed code
        tvInput?.text?.let {
            // it refers to whatever is existing if tvInput in not null
            // basically it gives us the text that is written inside tvInput
            if(lastNumeric && !isOperatorAdded(it.toString())){

                // append the text of the operator button pressed to the textView
                tvInput?.append((view as Button).text)

                lastNumeric=false
                lastDot=false
            }
        }
    }


    fun onEqual(view: View){

        var prefix=""

        if(lastNumeric){
            var tvValue=tvInput?.text.toString()
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"

                    tvValue=tvValue.substring(1)
                }

                if(tvValue.contains("-")){

                    val splitValue=tvValue.split("-") // returns the values in an array

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){

                    val splitValue=tvValue.split("+") // returns the values in an array

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()+two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){

                    val splitValue=tvValue.split("/") // returns the values in an array

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                }
                else if(tvValue.contains("*")){

                    val splitValue=tvValue.split("*") // returns the values in an array

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()*two.toDouble()).toString())
                }



            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result : String) : String{
        var value=result

        if(result.endsWith(".0")){
            value=result.substring(0, result.length-2)
        }

        return value
    }

    // this method is used to tell us if there is any operator symbol other than at starting
    private fun isOperatorAdded(value : String) : Boolean{
        // ignoring the '-' at the beginning of the value entered
        return if(value.startsWith("-")){
            false
        }
        else{
            // returns true if value contains either of the given symbols
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }
}