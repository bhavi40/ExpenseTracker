package com.example.expensetracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Expense {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo (name="expense")
    Float expense;
    @ColumnInfo (name="category")
    String category;
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "date")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public Expense(Float expense, String category) {
        this.id = 0;
        this.expense = expense;
        this.category = category;
        this.date = Calendar.getInstance().getTime();
    }

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public Float getExpense() {
        return expense;
    }

    public void setExpense(Float expense) {
        this.expense = expense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
