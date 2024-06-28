package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.expensetracker.Database.CategoryDatabase;
import com.example.expensetracker.databinding.ActivityExpenseReportBinding;
import com.example.expensetracker.databinding.ActivityTodayBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;

import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.ParseException;

public class ExpenseReportActivity extends MainActivity {
    private ActivityExpenseReportBinding binding;
    private ArrayList<Expense> list;
    private ExpenseListAdapter expenseListAdapter;

    private CategoryDatabase categoryDatabase;
    private ExpenseListAdapter.RecyclerViewClickListner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.expensesReportTotalCurrencyTextView.setText("USD");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        binding.dateTextView.setText(formattedDate);


        list = new ArrayList<Expense>();
        categoryDatabase = CategoryDatabase.getInstance(getApplicationContext());
        expenseListAdapter = new ExpenseListAdapter(this, list,listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(expenseListAdapter);
        handleIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expensereport_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_expenseFilter) {
            // Show the popup menu here
            View menuItemView = findViewById(R.id.action_expenseFilter); // SAME ID AS MENU ID
            PopupMenu popupMenu = new PopupMenu(this, menuItemView);
            getMenuInflater().inflate(R.menu.expensereport_items_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemId = item.getItemId();
                    if(itemId ==R.id.action_date) {
                        showDatePickerDialog();
                        return true;

                    }
                    else if (itemId == R.id.action_week) {
                        showWeek();
                        return true;

                    }
                    else if(itemId == R.id.action_month){
                        showMonth();
                        return true;
                    }
                    return false;
                }
            });

            popupMenu.show(); // Show the popup menu
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMonth() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        Date startDate = now.getTime();
        now.add(Calendar.MONTH, 1);
        now.add(Calendar.DATE, -1); // Move to the last day of the month
        Date endDate = now.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String formattedDateRange = dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
        binding.dateTextView.setText(formattedDateRange);
        fetchExpenses(startDate, endDate);


    }

    private void showWeek() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek());
        Date startOfWeek = now.getTime();
        now.add(Calendar.DATE, 6); // Move to the end of the week
        Date endOfWeek = now.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String formattedWeek = dateFormat.format(startOfWeek) + " - " + dateFormat.format(endOfWeek);
        binding.dateTextView.setText(formattedWeek);
        fetchExpenses(startOfWeek, endOfWeek);
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // This method gets called when a date is selected.
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String selectedDateString = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(selectedDate.getTime());
                        binding.dateTextView.setText(selectedDateString);

                        // You might want to call loadData() or other methods to refresh data based on the selected date.
                        loadDateData(selectedDateString);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void loadDateData(String selectedDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        Date selectedDate = null;
        try {
            selectedDate = dateFormat.parse(selectedDateString);
        } catch (ParseException e) {
            Log.e("DatePicker", "Error parsing date", e);
        }

        if (selectedDate == null) {
            return; // If parsing fails, exit the method early
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Set start of the day
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime(); // This is the start of the day

        calendar.add(Calendar.DATE, 1); // Add one day
        calendar.add(Calendar.MILLISECOND, -1); // Subtract one millisecond to get the end of the day
        Date dayEnd = calendar.getTime(); // This is the end of the day
        fetchExpenses(dayStart,dayEnd);


    }


    private void handleIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("NewExpense") && intent.hasExtra("selected_Category")) {
            Float NewExpense=intent.getFloatExtra("NewExpense",0f);
            String categoryName = intent.getStringExtra("selected_Category");
            Expense expense = new Expense(NewExpense,categoryName );
            //list.add(expense);
            long id = categoryDatabase.expenseDao().insertExpense(expense);
            expenseListAdapter.notifyDataSetChanged();
        }
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
        binding.expensesReportTotalCurrencyTextView.setText(currency);
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
        fetchExpenses(dayStart,dayEnd);

    }

    private void fetchExpenses(Date dayStart, Date dayEnd) {
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
        if (todayExpenses != null && !todayExpenses.isEmpty()) {
            list.clear();
            list.addAll(todayExpenses);
            expenseListAdapter.notifyDataSetChanged();
        }else{
            list.clear();
            expenseListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "No expenses recorded for the selected day.", Toast.LENGTH_LONG).show();
        }

        // Updating the TextView for the total expense
        String totalExpenseText = String.format(Locale.getDefault(), "%.2f", todayTotalExpense);
        binding.expensesReportTotalTextView.setText(totalExpenseText);
    }




}