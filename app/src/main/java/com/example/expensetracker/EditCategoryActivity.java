package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensetracker.databinding.ActivityEditCategoryBinding;

public class EditCategoryActivity extends MainActivity {
    private ActivityEditCategoryBinding binding;
    String oldCategory="";

    private CategoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            oldCategory = extras.getString("categoryName", "");
            binding.categoryNameEditText.setText(oldCategory);
        }

        binding.buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCategoryActivity.this, CategoryActivity.class);
                startActivity(intent);

            }
        });


        binding.buttonEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                 String newCategory=binding.categoryNameEditText.getText().toString();
                if (!newCategory.isEmpty() && !oldCategory.equals(newCategory)) {
                    viewModel.updateCategoryName(oldCategory, newCategory);
                    Toast.makeText(EditCategoryActivity.this, "Category updated", Toast.LENGTH_SHORT).show();
                    oldCategory = newCategory;  // Update oldCategory to reflect the new name
                } else {
                    Toast.makeText(EditCategoryActivity.this, "No changes made or field is empty", Toast.LENGTH_SHORT).show();
                }

               finish();

            }
        } );

        binding.buttonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!oldCategory.isEmpty()) {
                    viewModel.deleteCategoryByName(oldCategory);
                    finish();
                }

            }
        } );

    }
}