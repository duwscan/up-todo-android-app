package com.app.todoapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.viewmodels.CategoryViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private TextView mUserName;

    private EditText mUserNameInput;

    private Button mUpdateButton;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Category c1 = new Category();
        c1.setIcon("DucAn");
        c1.setName("DucAnName");
        categoryService = new CategoryService(this);
        categoryService.saveCategory(c1);
        setContentView(R.layout.activity_main);
        mUserName = findViewById(R.id.user_name);
        mUserNameInput = findViewById(R.id.user_name_input);
        mUpdateButton = findViewById(R.id.update_user);
        categoryViewModel = (CategoryViewModel) new ViewModelProvider(this, AppInjection.getViewModelFactory(this)).get(CategoryViewModel.class);
        mUpdateButton.setOnClickListener(v -> updateUserName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposable.add(categoryViewModel.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    if (!categories.isEmpty()) {
                        mUserName.setText(categories.get(categories.size() - 1).getName());
                    }
                }));
    }

    private void updateUserName() {
        String userName = mUserNameInput.getText().toString();
        Category c1 = new Category();
        c1.setName(userName);
        // Disable the update button until the user name update has been done
        mUpdateButton.setEnabled(false);
        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        mDisposable.add(categoryViewModel.save(c1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mUpdateButton.setEnabled(true),
                        throwable -> Log.e("Category", "Unable to create C", throwable)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
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