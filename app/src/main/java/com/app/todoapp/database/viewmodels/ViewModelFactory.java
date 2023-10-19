package com.app.todoapp.database.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.database.DaoInjection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(DaoInjection.categoryDAO(context));
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
