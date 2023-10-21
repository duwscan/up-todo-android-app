package com.app.todoapp.viewmodels;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.app.todoapp.database.DaoInjection;
import com.app.todoapp.viewmodels.task.TaskViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        SavedStateHandle savedStateHandle = createSavedStateHandle(extras);
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(DaoInjection.categoryDAO(context));
        }
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(DaoInjection.taskDAO(context), savedStateHandle);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
