package com.app.todoapp.database;

import android.content.Context;

import com.app.todoapp.database.categories.CategoryDAO;

public class DaoInjection {
    public static CategoryDAO categoryDAO(Context context) {
        return AppDatabase.getInstance(context).categoryDAO();
    }
}
