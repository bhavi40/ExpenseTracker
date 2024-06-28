package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.expensetracker.Database.CategoryDatabase;
import com.example.expensetracker.databinding.ActivityExpenseListEditBinding;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseListEditActivity extends MainActivity {

    private ActivityExpenseListEditBinding binding;
    private ExpenseViewModel viewModel;
    private Float expense;
    private String category="";
    private long id;
    private CategoryDatabase categoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseListEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryDatabase = CategoryDatabase.getInstance(getApplicationContext());
        viewModel = new ExpenseViewModel(getApplication());

        setupFromIntent();
        setupButtons();
    }

    private void setupFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            expense = extras.getFloat("Expense");
            category = extras.getString("CategoryName");
            id = extras.getLong("id");
            binding.expenseEditValueText.setText(String.valueOf(expense));
        }
        loadData();
    }

    private void loadData() {
        List<Category> categories = categoryDatabase.categoryDao().getAllCategories();
        List<String> categoryNames = categories.stream().map(Category::getCategoryName).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoryTypeEdit.setAdapter(adapter);

        if (!category.isEmpty()) {
            int spinnerPosition = adapter.getPosition(category);
            if (spinnerPosition >= 0) {
                binding.spinnerCategoryTypeEdit.setSelection(spinnerPosition);
            }
        }
    }

    private void setupButtons() {
        binding.buttonMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TodayActivity.class);
            startActivity(intent);
        });

        binding.buttonUpdate.setOnClickListener(v -> {
            try {
                Float newExpense = Float.parseFloat(binding.expenseEditValueText.getText().toString());
                String categoryName = binding.spinnerCategoryTypeEdit.getSelectedItem().toString();
                viewModel.updateExpenseList(newExpense, categoryName, id);
                Toast.makeText(ExpenseListEditActivity.this, "Expense updated", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.deleteExpense(id);
            Toast.makeText(ExpenseListEditActivity.this, "Expense deleted", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }
}
