package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.net.InterfaceAddress

class MainActivity : AppCompatActivity() {

    lateinit var name : EditText;   // declaration
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun ClickHandler(view: View){
        when(view.id){
            R.id.loginbtn->{loginbtn()}
        }
    }

    private fun loginbtn() {
        name = findViewById<EditText>(R.id.username)
        password = findViewById<EditText>(R.id.password)
        var uname = name.text.toString()
        var upass = password.text.toString()
        if(uname.length==0 || upass.length==0){
            if(uname.length==0){
                Toast.makeText(this,"Please enter user name", Toast.LENGTH_SHORT).show()
                return;
            }else{
                Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show()
                return;
            }
        }
        else if(uname!="student"){
            Toast.makeText(this,"Please enter correct username", Toast.LENGTH_SHORT).show()
        }else if(upass!="mtap"){
            Toast.makeText(this,"Please enter correct password", Toast.LENGTH_SHORT).show()
        }else{
            goHomeScreen()
        }
    }

    private fun goHomeScreen() {
         var HomeIntent : Intent
         HomeIntent = Intent(this,HomeActivity::class.java)
         startActivity(HomeIntent)
    }
}