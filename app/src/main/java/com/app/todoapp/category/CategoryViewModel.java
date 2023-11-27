package com.app.todoapp.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.app.todoapp.database.DaoInjection;
import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryDAO;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryDAO categoryDAO;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryDAO = DaoInjection.categoryDAO(application);
    }

    LiveData<Category> getById(int id) {
        return categoryDAO.getById(id);
    }


    public ListenableFuture<Long> save(Category category) {
        return categoryDAO.save(category);
    }

    public ListenableFuture<Integer> delete(Category category) {
        return categoryDAO.delete(category);
    }

    public LiveData<List<Category>> getAll() {
        return categoryDAO.getAll();
    }
}
