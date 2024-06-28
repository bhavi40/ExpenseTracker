package com.example.expensetracker.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.expensetracker.DateConverter;
import com.example.expensetracker.Expense;

import java.util.Date;
import java.util.List;
@TypeConverters(DateConverter.class)
@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM Expense WHERE date >= :dayStart AND date < :dayEnd")
    List<Expense> getAllExpensesForDay(Date dayStart, Date dayEnd);

    @Query("SELECT SUM(expense) FROM Expense WHERE date >= :dayStart AND date < :dayEnd")
    float getDayTotalExpense(Date dayStart, Date dayEnd);


    @Insert
    long insertExpense(Expense expense);

    @Query("UPDATE Expense SET expense = :newExpense, category = :categoryName WHERE id= :id")
    void updateExpenseList(Float newExpense, String categoryName, long id);

    @Query("DELETE FROM Expense WHERE id = :id")
    void deleteExpense(long id);
}

