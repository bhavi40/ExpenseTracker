package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.expensetracker.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

import com.example.expensetracker.Database.CategoryDatabase;
public class CategoryActivity extends MainActivity {

    private ActivityCategoryBinding binding;
    private List<Category> list;
    private CategoryAdapter categoryAdapter;
    private CategoryDatabase categoryDatabase;
    private CategoryAdapter.RecyclerViewClickListner listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListner();
        list = new ArrayList<Category>();
        categoryDatabase = CategoryDatabase.getInstance(getApplicationContext());
        categoryAdapter = new CategoryAdapter(this, list,listner);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewCategory.setLayoutManager(layoutManager);
        binding.recyclerViewCategory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerViewCategory.setAdapter(categoryAdapter);




    }
    private void setOnClickListner() {
        listner=new CategoryAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(), EditCategoryActivity.class);
                intent.putExtra("categoryName",list.get(position).getCategoryName());
                startActivity(intent);
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.activity_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_category) {
            Intent intent = new Intent(this, NewCategory.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("CategoryName")) {
            String categoryName = intent.getStringExtra("CategoryName");
            Category newCategory = new Category(categoryName);
            //list.add(new Category(categoryName));
            long id = categoryDatabase.categoryDao().insertCategory(newCategory);
            loadData();
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume () {
        super.onResume();
        loadData();
    }
    public void loadData () {

        List<Category> list2 = categoryDatabase.categoryDao().getAllCategories();
        list.clear();
        list.addAll (list2);
        categoryAdapter.notifyDataSetChanged();

    }


}