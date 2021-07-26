package com.example.calculator.ViewModel;

import android.content.Context;

import androidx.room.Room;

import com.example.calculator.model.MyDataBase;
import com.example.calculator.model.Student;

import java.util.List;


public class StudentViewModel {
    MyDataBase myDataBase;
    public StudentViewModel(Context context){
        myDataBase= Room.databaseBuilder(context,MyDataBase.class,"userdb").allowMainThreadQueries().build();
    }

    public void insertStudentData(Student student){
        myDataBase.myDAO().insert(student);
    }

    public List<Student> getAllData(){
        return myDataBase.myDAO().getAllData();
    }

}
