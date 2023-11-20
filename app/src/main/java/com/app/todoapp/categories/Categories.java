package com.app.todoapp.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {
    private RecyclerView rcvCate;
    private CategoryAdapter categoryAdapter;
    private Button button;

    public static List<Category> list = new ArrayList<>();

    protected void onCreate(Bundle savedInStanceState) {
        super.onCreate(savedInStanceState);
        Intent intent = getIntent();
        setContentView(R.layout.categories);

        getData(list);

        button = findViewById(R.id.addCategories);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_categories();
            }
        });

        onActivityResult(0, RESULT_OK, intent);
        categoryAdapter.notifyDataSetChanged();
    }

    private void create_categories() {
        Intent intent = new Intent(Categories.this, CreateCategories.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data != null) {
                Bundle bundle = getIntent().getExtras();
                if (bundle == null) {
                    return;
                }
                Category category = (Category) bundle.get("new_category_icon");
                String newCategoryName = data.getStringExtra("new_category_name");
                int newCategoryIcon = category.getUid();

                Category newCategory = new Category(newCategoryIcon, newCategoryName);
                list.add(newCategory);
                categoryAdapter.notifyItemInserted(list.size() - 1);
            }
        }
    }

    public void getData(List<Category> list) {
        rcvCate = findViewById(R.id.categoryRecycler);
        categoryAdapter = new CategoryAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvCate.setLayoutManager(layoutManager);
        rcvCate.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }
}
