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

    protected void onCreate(Bundle savedInStanceState){
        super.onCreate(savedInStanceState);
        Intent intent = getIntent();
        setContentView(R.layout.categories);

        rcvCate = findViewById(R.id.categoryRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCate.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(getListCategories());

        rcvCate.setAdapter(categoryAdapter);

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

    private List<Category> getListCategories() {
//        list.add(new Category(R.drawable.category_grocery, "Grocery"));
//        list.add(new Category(R.drawable.category_sport, "Sport"));
//        list.add(new Category(R.drawable.category_design, "Design"));
//        list.add(new Category(R.drawable.category_university, "University"));
//        list.add(new Category(R.drawable.category_social, "Social"));
//        list.add(new Category(R.drawable.category_music, "Music"));
//        list.add(new Category(R.drawable.category_health, "Health"));
//        list.add(new Category(R.drawable.category_movie, "Movie"));
//        list.add(new Category(R.drawable.category_home, "Home"));

        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data != null) {
                Bundle bundle = getIntent().getExtras();
                if(bundle == null){
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
}
