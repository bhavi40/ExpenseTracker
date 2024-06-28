package com.example.expensetracker.Database;
import com.example.expensetracker.Category;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("Select * from Category")
    public List<Category> getAllCategories();

    @Insert
    public long insertCategory (Category category);

    @Query("UPDATE Category SET category = :newName WHERE category = :oldName")
    void updateCategoryByName(String oldName, String newName);

    @Query("DELETE FROM Category WHERE category = :categoryName")
    void deleteCategoryByName(String categoryName);


}


