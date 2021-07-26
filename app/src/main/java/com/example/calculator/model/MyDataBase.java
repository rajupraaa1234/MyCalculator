package com.example.calculator.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDAO myDAO();
}
