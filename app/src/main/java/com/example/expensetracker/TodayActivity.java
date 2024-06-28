package com.example.expensetracker;

import com.example.expensetracker.Database.CategoryDatabase;


import android.content.Intent;

import android.content.SharedPreferences;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.expensetracker.databinding.ActivityTodayBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayActivity extends MainActivity {

     ActivityTodayBinding binding;
     private ArrayList<Expense> list;
     private ExpenseListAdapter expenseListAdapter;

    private CategoryDatabase categoryDatabase;
    private ExpenseListAdapter.RecyclerViewClickListner listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListner();


        binding.totalExpenseSumTextView.setText("0.00");



        list = new ArrayList<Expense>();
        categoryDatabase = CategoryDatabase.getInstance(getApplicationContext());
        expenseListAdapter = new ExpenseListAdapter(this, list,listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(expenseListAdapter);
        handleIntent();


    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("NewExpense") && intent.hasExtra("selected_Category")) {
            Float NewExpense=intent.getFloatExtra("NewExpense",0f);
            String categoryName = intent.getStringExtra("selected_Category");
            Expense expense = new Expense(NewExpense,categoryName );
            //list.add(expense);
           long id = categoryDatabase.expenseDao().insertExpense(expense);
            loadData();
            expenseListAdapter.notifyDataSetChanged();

        }
    }

    private void setOnClickListner() {
        listner=new ExpenseListAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(), ExpenseListEditActivity.class);

                intent.putExtra("Expense",list.get(position).getExpense());
                intent.putExtra("CategoryName",list.get(position).getCategory());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("contactAdapterPosition", position);
                startActivity(intent);
                //startActivityForResult(intent, 1);

            }
        };
    }
    @Override
    public void onResume () {
        super.onResume();
        loadCurrencyPreference();
        loadData();
    }

    private void loadCurrencyPreference() {
        SharedPreferences prefs = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        String currencyType = prefs.getString("SelectedCurrency", "USDollar(USD)");
        String currency;

        switch (currencyType) {
            case "USDollar(USD)":
                currency = "USD";
                break;
            case "Euro(EUR)":
                currency = "EUR";
                break;
            case "BritishPound(GBP)":
                currency = "GBP";
                break;
            case "CanadianDollar(CAD)":
                currency = "CAD";
                break;
            case "AustralianDollar(AUD)":
                currency = "AUD";
                break;
            case "Ukrainian Hryvnia (UAH)":
                currency = "UAH";
                break;
            case "RussianRuble(RUB)":
                currency = "RUB";
                break;
            default:
                currency = "USD"; // default case if none of the expected values are found
                break;
        }
        binding.totalExpenseCurrencyTextView.setText(currency);
    }

    private void loadData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();

        // Fetch expenses for the specified day range
        List<Expense> todayExpenses = null;
        float todayTotalExpense = 0;
        try {
            todayExpenses = categoryDatabase.expenseDao().getAllExpensesForDay(dayStart, dayEnd);
            todayTotalExpense = categoryDatabase.expenseDao().getDayTotalExpense(dayStart, dayEnd);
        } catch (Exception e) {
            // Handle potential exceptions, such as database access errors
            Log.e("loadData", "Error loading data", e);
        }

        // Clearing the previous data in the list and updating it with new data
        if (todayExpenses != null) {
            list.clear();
            list.addAll(todayExpenses);
            expenseListAdapter.notifyDataSetChanged();


        }

        // Updating the TextView for the total expense
        String totalExpenseText = String.format(Locale.getDefault(), "%.2f", todayTotalExpense);
        binding.totalExpenseSumTextView.setText(totalExpenseText);
    }


    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.activity_today_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_expense) {
            Intent intent=new Intent(this, NewExpense.class);
           startActivity(intent);
        }

        return true;
    }




}