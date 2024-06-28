package com.example.expensetracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.expensetracker.Database.CategoryDatabase;
import com.example.expensetracker.Database.CategoryDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryDao categoryDao;
    private ExecutorService executorService;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        CategoryDatabase db = CategoryDatabase.getInstance(application);
        categoryDao = db.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Method to update a category by name
    public void updateCategoryName(String oldName, String newName) {
        executorService.execute(() -> {
            categoryDao.updateCategoryByName(oldName, newName);
        });
    }

    // Method to delete a category by name
    public void deleteCategoryByName(String categoryName) {
        executorService.execute(() -> {
            categoryDao.deleteCategoryByName(categoryName);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
