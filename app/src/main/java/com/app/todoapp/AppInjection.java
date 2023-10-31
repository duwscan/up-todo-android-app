package com.app.todoapp;

import android.content.Context;

import com.app.todoapp.common.ViewModelFactory;

public class AppInjection {
    public static ViewModelFactory getViewModelFactory(Context context) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(context);
        return viewModelFactory;
    }
}
