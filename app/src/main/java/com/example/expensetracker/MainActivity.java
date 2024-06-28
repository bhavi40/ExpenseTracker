package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.expensetracker.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public void setContentView(View view) {
        drawerLayout=(DrawerLayout)getLayoutInflater().inflate(R.layout.activity_main,null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView=drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.nav_today){
            //Toast.makeText(getApplicationContext(),"Today",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TodayActivity.class));
            overridePendingTransition(0,0);

        }
        else if(item.getItemId()==R.id.nav_expense){
            startActivity(new Intent(this, ExpenseReportActivity.class));
            overridePendingTransition(0,0);


        }
        else if(item.getItemId()==R.id.nav_category){
            startActivity(new Intent(this, CategoryActivity.class));
            overridePendingTransition(0,0);


        }else if(item.getItemId() == R.id.nav_settings){
            startActivity(new Intent(this,SettingsActivity.class));
            overridePendingTransition(0,0);
        }

        return true;

    }
}