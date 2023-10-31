package com.app.todoapp.common;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryDAO;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<List<Category>> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
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

    public LiveData<List<Category>> getData() {
        return data;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<List<Category>> fetchData() {
//        isLoading.setValue(true);
        // Simulate fetching dLiveData<List<Category>>ata from a data source
        return Transformations.switchMap(isLoading, aBoolean -> this.getAll());
    }

    public LiveData<List<Category>> getAll() {
        return this.categoryDAO.getAll();
    }

    public LiveData<Category> getById(int id) {
        return categoryDAO.getById(id);
    }
}
