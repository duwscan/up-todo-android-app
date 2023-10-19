package com.app.todoapp.database.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Query;

import com.app.todoapp.database.DaoInjection;
import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryDAO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class CategoryViewModel extends ViewModel {

    private CategoryDAO categoryDAO;

    public CategoryViewModel(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public Flowable<Category> getByName(String name) {
        return this.categoryDAO.getByName(name);
    }

    public Completable save(Category... categories) {
        return this.categoryDAO.save(categories);
    }

    public Flowable<List<Category>> getAll() {
        return this.categoryDAO.getAll();
    }

}
