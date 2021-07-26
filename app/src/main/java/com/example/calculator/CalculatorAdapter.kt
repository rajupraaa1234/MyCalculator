package com.example.calculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.model.Student
import com.example.myloginapp.interfacePackage.OnClickListner

class CalculatorAdapter(context: DashBoard, data: ArrayList<Student>) :RecyclerView.Adapter<CalculatorAdapter.ViewHolder>() {

    var context : Context = context
    var arr : ArrayList<Student> = data
    var onclickItem : OnClickListner = context

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
       var textview : TextView = itemView.findViewById(R.id.txt)
        var delbtn : Button = itemView.findViewById<Button>(R.id.delbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.textview.text = arr.get(position).Uname
        holder.textview.setOnClickListener(View.OnClickListener {
            onclickItem.onItemClick(position)
        })
        holder.delbtn.setOnClickListener(View.OnClickListener {
            onclickItem.onDeleteClick(position)
        })
    }

    override fun getItemCount(): Int {
       return arr.size
    }

}