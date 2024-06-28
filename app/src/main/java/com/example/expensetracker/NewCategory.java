package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.AlertDialog;

import com.example.expensetracker.databinding.ActivityNewCategoryBinding;


public class NewCategory extends MainActivity {

    private ActivityNewCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddCategory();
    }

    private void AddCategory() {
        binding.buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategory = binding.categoryNameEditText.getText().toString();
                Intent intent = new Intent(NewCategory.this, CategoryActivity.class);
                intent.putExtra("CategoryName", newCategory);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
