package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.expensetracker.databinding.DialogViewCurrencyBinding;

import java.util.Locale;

public class DialogViewCurrency extends DialogFragment {

    private DialogViewCurrencyBinding binding;
    private CurrencyListener currencyListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogViewCurrencyBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        binding.buttonCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        SharedPreferences prefs = getActivity().getSharedPreferences("CurrencyPrefs", Context.MODE_PRIVATE);
        String selectedCurrency = prefs.getString("SelectedCurrency", "US Dollar (USD)");
        setSelectedCurrency(selectedCurrency);


        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String currencyType = "";

                if (binding.radioButtonUs.isChecked()){
                    currencyType = "USDollar(USD)";

                }
                else if (binding.radioButtonEuro.isChecked()){
                    currencyType = "Euro(EUR)";

                }
                else if (binding.radioButtonPound.isChecked()){
                    currencyType = "BritishPound(GBP)";

                }

                else if(binding.radioButtonCd.isChecked()){
                    currencyType="CanadianDollar(CAD)";

                }
                else if(binding.radioButtonAud.isChecked()){
                    currencyType="AustralianDollar(AUD)";

                }
                else if(binding.radioButtonUh.isChecked()){
                    currencyType="Ukrainian Hryvnia (UAH)";


                }
                else if(binding.radioButtonRr.isChecked()){
                    currencyType="RussianRuble(RUB)";

                }
                currencyListener.onCurrencySelected(currencyType);

            }
        });
        return builder.create();

    }

    private void setSelectedCurrency(String selectedCurrency) {
        switch (selectedCurrency) {
            case "USDollar(USD)":
                binding.radioButtonUs.setChecked(true);
                break;
            case "Euro(EUR)":
                binding.radioButtonEuro.setChecked(true);
                break;
            case "BritishPound(GBP)":
                binding.radioButtonPound.setChecked(true);
                break;
            case "CanadianDollar(CAD)":
                binding.radioButtonCd.setChecked(true);
                break;
            case "AustralianDollar(AUD)":
                binding.radioButtonAud.setChecked(true);
                break;
            case "Ukrainian Hryvnia (UAH)":
                binding.radioButtonUh.setChecked(true);
                break;
            case "RussianRuble(RUB)":
                binding.radioButtonRr.setChecked(true);
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            currencyListener = (CurrencyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CurrencyListener");
        }
    }
    public interface CurrencyListener {
        void onCurrencySelected(String currencyType);
    }
}
