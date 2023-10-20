package com.app.todoapp.database;

import android.content.Context;

import com.app.todoapp.database.categories.CategoryDAO;
import com.app.todoapp.database.task.TaskDAO;

public class DaoInjection {
    public static CategoryDAO categoryDAO(Context context) {
        return AppDatabase.getInstance(context).categoryDAO();
    }

    public static TaskDAO taskDAO(Context context) {
        return AppDatabase.getInstance(context).taskDAO();
    }
}
