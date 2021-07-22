package com.example.calculator

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class DashBoard : AppCompatActivity(){

    lateinit var toolbar :Toolbar
    lateinit var toggle : ImageView
    lateinit var drawer_layout : DrawerLayout
    lateinit var navigationView : NavigationView

    lateinit var info: TextView

    private var prev: Char = '\u0000'
    lateinit var result: TextView
    private val ADDITION = '+'
    private val SUBTRACTION = '-'
    private val MULTIPLICATION = '*'
    private val DIVISION = '/'
    private val EQU = 0.toChar()
    private var val1 = Double.NaN
    private var val2 = 0.0
    var doubleBackToExitPressedOnce = false
    private var ACTION = 0.toChar()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        //init
        info = findViewById(R.id.res)
        result = findViewById(R.id.result)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toggle = findViewById(R.id.toggle)
        drawer_layout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        //Start set up
        setSupportActionBar(toolbar)
        toggle.setOnClickListener(View.OnClickListener {
            NavigationClickHandler()
        })

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mynav_home -> {
                    //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.myhistory -> {
                    Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }

        }

    }



    fun NavigationClickHandler(){
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        openDialog()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
        if(info.text.length==0){
            return
        }
        if(checkPrev('=') || checkInitialPoint()==true){
            return
        }else{
            prev = '='
        }
        compute()
        ACTION = EQU
        result.text = result.text.toString() + val2.toString() + "=" + val1.toString()
        info.text = val1.toString()
    }

    private fun pressPoint() {
        if(!checkBeforePoint()) {
            info.setText(info.text.toString() + ".")
        }
    }

    private fun checkBeforePoint(): Boolean {
        var str = info.text.toString()
        for(i in str){
            if(i=='.'){
                return true
            }
        }
        return false
    }

    private fun backMehod() {
        if(info.text.toString().length>0)
            info.setText(info.text.toString().subSequence(0,info.text.length-1))
    }

    private fun clearMethod() {
        val1 = Double.NaN
        val2 = Double.NaN
        info.text = null
        result.text = null
        prev ='\u0000'
    }

    private fun pressNine() {
        prev = '9'
        info.setText(info.text.toString() + "9")
    }

    private fun pressEight() {
        prev = '8'
        info.setText(info.text.toString() + "8")
    }

    private fun pressSeven() {
        prev = '7'
        info.setText(info.text.toString() + "7")
    }

    private fun pressSix() {
        prev = '6'
        info.setText(info.text.toString() + "6")
    }

    private fun pressFive() {
        prev = '5'
        info.setText(info.text.toString() + "5")
    }

    private fun pressFour() {
        prev = '4'
        info.setText(info.text.toString() + "4")
    }

    private fun pressThree() {
        prev = '3'
        info.setText(info.text.toString() + "3")
    }

    private fun pressTwo() {
        prev = '2'
        info.setText(info.text.toString() + "2")
    }

    private fun pressOne() {
        prev = '1'
        info.setText(info.text.toString() + "1")
    }

    private fun pressZero() {
        prev = '0'
        info.setText(info.text.toString() + "0")
    }

    private fun divideMethod() {
        if(info.text.length==0){
            return
        }
        if(checkPrev('/') || checkInitialPoint() == true){
            return
        }else{
            prev = '/'
        }
        compute()
        ACTION = DIVISION
        result.text = "$val1/"
        info.text = null
    }

    private fun mulMethod() {
        if(info.text.length==0){
            return
        }
        if(checkPrev('*') || checkInitialPoint() == true){
            return
        }else{
            prev = '*'
        }
        compute()
        ACTION = MULTIPLICATION
        result.text = "$val1*"
        info.text = null
    }

    private fun subMethod() {
        if(info.text.length==0){
            return
        }
        if(checkPrev('-') ||checkInitialPoint() == true){
            return
        }else{
            prev = '-'
        }
        compute()
        ACTION = SUBTRACTION
        result.text = "$val1-"
        info.text = null
    }

    private fun addMethod() {
        if(info.text.length==0){
            return
        }
        if(checkPrev('+') || checkInitialPoint() == true){
            return
        }else{
            prev = '+'
        }
        compute()
        ACTION = ADDITION
        result.text = "$val1+"
        info.text = null
    }

    private fun compute() {
        if (!java.lang.Double.isNaN(val1)) {
            val2 = info.getText().toString().toDouble()
            when (ACTION) {
                ADDITION -> val1 = val1 + val2
                SUBTRACTION -> val1 = val1 - val2
                MULTIPLICATION -> val1 = val1 * val2
                DIVISION -> val1 = val1 / val2
                EQU -> {
                }
            }
        } else {
            val1 = info.getText().toString().toDouble()
        }
    }
    private fun checkPrev(ch: Char): Boolean{
        if(ch== this.prev || prev=='+' || prev == '-' || prev == '*' || prev =='/' ) {
            return true
        }
        return false
    }

    private fun checkInitialPoint(): Boolean? {
        return if (info.text.toString().length == 1 && info.text.toString()[0] == '.') {
            true
        } else false
    }




    private fun openDialog(){
        val alertDialog: AlertDialog? = this?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        super.onBackPressed()
                    })
                setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            builder.setMessage("Do you want to cancel?")
            builder.create()
        }

        with(alertDialog) {

            this?.show()
        }

    }
}