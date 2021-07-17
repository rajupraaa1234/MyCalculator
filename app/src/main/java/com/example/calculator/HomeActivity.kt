package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    lateinit var resText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        resText = findViewById(R.id.res)
    }

    fun HomeClcikHndler(view: View){
        when(view.id){
            R.id.addlin->{addMethod()}
            R.id.sublin->{subMethod()}
            R.id.mullin->{mulMethod()}
            R.id.divlin->{divideMethod()}
            R.id.lin0->{pressZero()}
            R.id.lin1->{pressOne()}
            R.id.lin2->{pressTwo()}
            R.id.lin3->{pressThree()}
            R.id.lin4->{pressFour()}
            R.id.lin5->{pressFive()}
            R.id.lin6->{pressSix()}
            R.id.lin7->{pressSeven()}
            R.id.lin8->{pressEight()}
            R.id.lin9->{pressNine()}
            R.id.clearlin->{clearMethod()}
            R.id.backlin->{backMehod()}
            R.id.pointlin->{pressPoint()}
            R.id.equallin->{pressEqual()}
        }
    }

    private fun pressEqual() {
        Toast.makeText(this,"=", Toast.LENGTH_SHORT).show()
    }

    private fun pressPoint() {
        if(!checkBeforePoint()) {
            resText.setText(resText.text.toString() + ".")
        }
        //Toast.makeText(this,".", Toast.LENGTH_SHORT).show()
    }

    private fun checkBeforePoint(): Boolean {
        var str = resText.text.toString()
        for(i in str){
            if(i=='.'){
                return true
            }
        }
        return false
    }

    private fun backMehod() {
        if(resText.text.toString().length>0)
            resText.setText(resText.text.toString().subSequence(0,resText.text.length-1))
        //Toast.makeText(this,"back", Toast.LENGTH_SHORT).show()
    }

    private fun clearMethod() {
        resText.setText("")
       // Toast.makeText(this,"clear", Toast.LENGTH_SHORT).show()
    }

    private fun pressNine() {
        resText.setText(resText.text.toString() + "9")
        //Toast.makeText(this,"9", Toast.LENGTH_SHORT).show()
    }

    private fun pressEight() {
        resText.setText(resText.text.toString() + "8")
        //Toast.makeText(this,"8", Toast.LENGTH_SHORT).show()
    }

    private fun pressSeven() {
        resText.setText(resText.text.toString() + "7")
        //Toast.makeText(this,"7", Toast.LENGTH_SHORT).show()
    }

    private fun pressSix() {
        resText.setText(resText.text.toString() + "6")
        //Toast.makeText(this,"6", Toast.LENGTH_SHORT).show()
    }

    private fun pressFive() {
        resText.setText(resText.text.toString() + "5")
        //Toast.makeText(this,"5", Toast.LENGTH_SHORT).show()
    }

    private fun pressFour() {
        resText.setText(resText.text.toString() + "4")
        //Toast.makeText(this,"4", Toast.LENGTH_SHORT).show()
    }

    private fun pressThree() {
        resText.setText(resText.text.toString() + "3")
        //Toast.makeText(this,"3", Toast.LENGTH_SHORT).show()
    }

    private fun pressTwo() {
        resText.setText(resText.text.toString() + "2")
        //Toast.makeText(this,"2", Toast.LENGTH_SHORT).show()
    }

    private fun pressOne() {
        resText.setText(resText.text.toString() + "1")
        //Toast.makeText(this,"1", Toast.LENGTH_SHORT).show()
    }

    private fun pressZero() {
        resText.setText(resText.text.toString() + "0")
        //Toast.makeText(this,"0", Toast.LENGTH_SHORT).show()
    }

    private fun divideMethod() {

        Toast.makeText(this,"divide", Toast.LENGTH_SHORT).show()
    }

    private fun mulMethod() {
        Toast.makeText(this,"mul", Toast.LENGTH_SHORT).show()
    }

    private fun subMethod() {
        Toast.makeText(this,"sub", Toast.LENGTH_SHORT).show()
    }

    private fun addMethod() {
        Toast.makeText(this,"add", Toast.LENGTH_SHORT).show()
    }
}