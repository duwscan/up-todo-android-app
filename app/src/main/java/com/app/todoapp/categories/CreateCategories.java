package com.app.todoapp.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateCategories extends Activity {
    private RecyclerView rcvCreCate;
    private CreateCategoryAdapter createCategoryAdapter;
    private TextView name;
    List<Category> list = new ArrayList<>();
    int icon;
    AppCompatButton buttonCancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_categories);

        name = findViewById(R.id.categoryName);

        rcvCreCate = findViewById(R.id.createCategoryRecycler);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        rcvCreCate.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCreCate.addItemDecoration(itemDecoration);

        createCategoryAdapter = new CreateCategoryAdapter(getListCategories(), new IClickItemCategoryListener(){
            public void OnClickItemCategory(Category category){ onClickNewCategory(category); }
        });

        rcvCreCate.setAdapter(createCategoryAdapter);

        buttonCancel = findViewById(R.id.cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnBack();
            }
        });
    }

    private void turnBack() {
        Intent intent = new Intent(CreateCategories.this, Categories.class);
        startActivity(intent);
    }

    private void onClickNewCategory(Category category) {
        String categoryName;
        categoryName = name.getText().toString();

        if(TextUtils.isEmpty(categoryName)){
            Toast.makeText(this, "Hãy nhập vào category name !", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Categories.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("new_category_icon", category);
            intent.putExtras(bundle);
            intent.putExtra("new_category_name", categoryName);
            startActivity(intent);
        }
    }

    private List<Category> getListCategories() {
        list.add(new Category(R.drawable.category_grocery, ""));
        list.add(new Category(R.drawable.category_sport, ""));
        list.add(new Category(R.drawable.category_design, ""));
        list.add(new Category(R.drawable.category_university, ""));
        list.add(new Category(R.drawable.category_social, ""));
        list.add(new Category(R.drawable.category_music, ""));
        list.add(new Category(R.drawable.category_health, ""));
        list.add(new Category(R.drawable.category_movie, ""));
        list.add(new Category(R.drawable.category_home, ""));

        return list;
    }
}
