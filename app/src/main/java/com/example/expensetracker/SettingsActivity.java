package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.expensetracker.databinding.ActivitySettingsBinding;
import com.example.expensetracker.databinding.ActivityTodayBinding;

import java.util.Locale;

public class SettingsActivity extends MainActivity implements DialogViewCurrency.CurrencyListener {
    private ActivitySettingsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadCurrencyPreference();

        binding.currency.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogViewCurrency dialogViewCurrency = new DialogViewCurrency();
               dialogViewCurrency.show(getSupportFragmentManager(), "");
           }
       });
    }
    private void loadCurrencyPreference() {
        SharedPreferences prefs = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        String currency = prefs.getString("SelectedCurrency", "US Dollar(USD)"); // Default to USD
        binding.selectedCurrencyTextView.setText(currency);
    }


    public void onCurrencySelected(String currencyType) {
        binding.selectedCurrencyTextView.setText(currencyType);
        saveCurrencyPreference(currencyType);
    }

    private void saveCurrencyPreference(String currencyType) {
        SharedPreferences prefs = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SelectedCurrency", currencyType);
        editor.apply();

    }
}