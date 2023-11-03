package com.app.todoapp.common;

import android.view.View;

import com.app.todoapp.database.task.Task;

public interface OnSaveTask {
    void onSaveTask(Task task);
}
