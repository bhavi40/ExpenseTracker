package com.example.expensetracker;


import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.expensetracker.Database.CategoryDatabase;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;


import com.example.expensetracker.databinding.NewExpenseBinding;

import java.util.List;
import java.util.stream.Collectors;


public class NewExpense extends MainActivity {
    private NewExpenseBinding binding;
    private CategoryDatabase categoryDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NewExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryDatabase = CategoryDatabase.getInstance(getApplicationContext());
        loadData();
    }

    @Override
    public void onResume () {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<Category> categories = categoryDatabase.categoryDao().getAllCategories();
        List<String> categoryNames = categories.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        binding.spinnerCategoryType.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.new_expense_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_addexpense) {
            Float newExpense = Float.parseFloat(binding.expenseValueEditText.getText().toString());
            String selectedCategory=binding.spinnerCategoryType.getSelectedItem().toString();

            Intent intentSummary = new Intent(this, ExpenseReportActivity.class);
            intentSummary.putExtra("NewExpense", newExpense);
            intentSummary.putExtra("selected_Category", selectedCategory);
            startActivity(intentSummary);

            finish();


            Intent intent=new Intent(this, TodayActivity.class);
            intent.putExtra("NewExpense",newExpense);
            intent.putExtra("selected_Category", selectedCategory);
            startActivity(intent);




            return true;



        }
        return true;
    }




}
