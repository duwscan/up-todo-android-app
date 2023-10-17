package com.app.todoapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryService;

public class MainActivity extends AppCompatActivity {
    private CategoryService categoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Category c1 = new Category();
        c1.setIcon("DucAn");
        c1.setName("DucAnName");
        categoryService = new CategoryService(this);
        categoryService.saveCategory(c1);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView view = findViewById(R.id.text);
        // do upadte data
        categoryService.getAllCategory(categories -> view.setText(categories.get(0).getName()), error -> view.setText("Error"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.categoryService.destroy();
    }
}