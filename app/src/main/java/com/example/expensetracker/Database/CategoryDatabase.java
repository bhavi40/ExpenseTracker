package com.example.expensetracker.Database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.expensetracker.Category;
import com.example.expensetracker.Expense;

@Database(entities = {Category.class, Expense.class}, version = 2)
public abstract class CategoryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "category.db";
    private static CategoryDatabase categoryDatabase;


    public static CategoryDatabase getInstance(Context context) {
        if (categoryDatabase == null) {
            categoryDatabase= Room.databaseBuilder( context, CategoryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }

        return  categoryDatabase;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Expense (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, expense REAL, date INTEGER, category TEXT)");
        }
    };


    public abstract CategoryDao categoryDao();
    public abstract ExpenseDao expenseDao();

}
