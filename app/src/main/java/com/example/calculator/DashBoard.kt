package com.example.calculator

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.calculator.ViewModel.StudentViewModel
import com.example.calculator.model.Student
import com.example.myloginapp.interfacePackage.OnClickListner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class DashBoard : AppCompatActivity(),OnClickListner{

    lateinit var toolbar :Toolbar
    lateinit var toggle : ImageView
    lateinit var drawer_layout : DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var hisLin :RelativeLayout
    lateinit var calLin :LinearLayout
    lateinit var tabTitle : TextView

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

    lateinit var adapter: CalculatorAdapter

    lateinit var recyclerView: RecyclerView
    lateinit var send : Button
    lateinit var msg : EditText
    lateinit var nodata : TextView
    lateinit var mdatabase : StudentViewModel


    var arr :ArrayList<Student> = ArrayList<Student>();

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        //init
        mdatabase = StudentViewModel(this)
        info = findViewById(R.id.res)
        result = findViewById(R.id.result)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toggle = findViewById(R.id.toggle)
        drawer_layout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        calLin = findViewById(R.id.calLin)
        hisLin = findViewById(R.id.HisLin)
        tabTitle = findViewById(R.id.tabTitle)
        recyclerView =  findViewById(R.id.recycle)
        nodata = findViewById(R.id.nodata)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalculatorAdapter(this,arr)
        recyclerView.adapter = adapter


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
                    calLin.visibility = View.VISIBLE
                    hisLin.visibility = View.GONE
                    tabTitle.setText("Calculator")
                    true
                }
                R.id.myhistory -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    calLin.visibility = View.GONE
                    hisLin.visibility = View.VISIBLE
                    tabTitle.setText("Calculator History")
                    if(checkForData()==true){
                        recyclerView.visibility=View.GONE
                        nodata.visibility = View.VISIBLE
                    }else{
                        fetchAllData()
                        recyclerView.visibility=View.VISIBLE
                        nodata.visibility = View.GONE
                    }
                    true
                }
                else -> false
            }

        }

    }

    private fun checkForData(): Boolean {
        var arrayList = ArrayList<Student>()
        arrayList = mdatabase.allData as ArrayList<Student>
        if(arrayList.size==0){
            return true
        }
        return false
    }

    private fun fetchAllData() {
        arr.clear()
        var arrayList = ArrayList<Student>()
        arrayList = mdatabase.allData as ArrayList<Student>
        for(i in arrayList){
            arr.add(i)
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
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            openDialog()
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
        addDataToRD(result.text.toString() + val2.toString() + "=" + val1.toString())
        //arr.add(result.text.toString() + val2.toString() + "=" + val1.toString())
        //adapter.notifyDataSetChanged()
        result.text = result.text.toString() + val2.toString() + "=" + val1.toString()
        info.text = val1.toString()
    }

    private fun addDataToRD(s: String) {
        var obj : Student = Student()
        obj.Uname= s
        mdatabase.insertStudentData(obj)
        arr.add(obj)
        //arr.add(result.text.toString() + val2.toString() + "=" + val1.toString())
        adapter.notifyDataSetChanged()
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

    override fun onItemClick(i: Int) {

    }

    override fun onDeleteClick(i: Int) {
        mdatabase.deleteData(arr.get(i))
        arr.removeAt(i)
        adapter.notifyDataSetChanged()
    }
}