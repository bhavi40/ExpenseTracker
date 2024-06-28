package com.example.expensetracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.expensetracker.Database.ExpenseDao;
import com.example.expensetracker.Database.CategoryDatabase;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseDao expenseDao;
    private ExecutorService executorService;
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        CategoryDatabase db = CategoryDatabase.getInstance(application);
        expenseDao = db.expenseDao();
        executorService = Executors.newSingleThreadExecutor();
    }
    public void updateExpenseList(Float newExpense, String categoryName, long id) {
        executorService.execute(() -> {
            expenseDao.updateExpenseList(newExpense,categoryName,id);
        });
    }

    // Method to delete a category by name
    public void deleteExpense(long id) {
        executorService.execute(() -> {
            expenseDao.deleteExpense(id);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
