package com.example.expensetracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo (name="category")
    private String categoryName;

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.id = 0;
        this.categoryName = categoryName;
    }



}
