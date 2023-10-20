package com.app.todoapp.viewmodels;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryDAO;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private CategoryDAO categoryDAO;

    public CategoryViewModel(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public LiveData<Category> getByName(String name) {
        return this.categoryDAO.getByName(name);
    }

    public ListenableFuture<Long> save(Category categories) {
        return this.categoryDAO.save(categories);
    }

    public LiveData<String> getData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void fetchData() {
        isLoading.setValue(true);
        // Simulate fetching data from a data source
        new Handler().postDelayed(() -> {
            data.setValue("Fetched data");
            isLoading.setValue(false);
        }, 2000);
    }

    public LiveData<List<Category>> getAll() {
        return this.categoryDAO.getAll();
    }

    public LiveData<Category> getById(int id) {
        return categoryDAO.getById(id);
    }
}
