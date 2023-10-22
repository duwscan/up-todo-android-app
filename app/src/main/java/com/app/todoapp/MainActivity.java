package com.app.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.viewmodels.CategoryViewModel;
import com.app.todoapp.viewmodels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private TextView mUserName;

    private EditText mUserNameInput;

    private Button mUpdateButton;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private List<Category> categoryList = new ArrayList<>();
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserName = findViewById(R.id.user_name);
        mUserNameInput = findViewById(R.id.user_name_input);
        mUpdateButton = findViewById(R.id.update_user);
        categoryViewModel = new ViewModelProvider(this, AppInjection.getViewModelFactory(this)).get(CategoryViewModel.class);
        taskViewModel = new ViewModelProvider(this, AppInjection.getViewModelFactory(this)).get(TaskViewModel.class);
        mUpdateButton.setOnClickListener(v -> updateUserName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Category c1 = new Category();
        c1.setName("Duc An");
        categoryViewModel.isLoading().observe(this, aBoolean -> {
            this.mUserName.setText(aBoolean ? "isLoading" : "EndLoading");
        });
        categoryViewModel.fetchData();

//        categoryViewModel.getAll().observe(this, categories -> {
//            if (!categories.isEmpty()) {
//                mUserName.setText(categories.get(categories.size() - 1).getName());
//            }
//        });

    }

    private void updateUserName() {
        String userName = mUserNameInput.getText().toString();
        Category c1 = new Category();
        c1.setName(userName);
        categoryViewModel.save(c1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }
}