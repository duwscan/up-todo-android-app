package com.app.todoapp.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskDAO;
import com.app.todoapp.database.task.TaskWithCategory;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskDAO taskDAO;

    public TaskViewModel(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public LiveData<List<TaskWithCategory>> getAllTasksWithCategories() {
        return taskDAO.getAllTasksWithCategories();
    }

    public LiveData<List<Task>> getAll() {
        return taskDAO.getAll();
    }

    public LiveData<List<TaskWithCategory>> getByDate(long date) {

        return taskDAO.getTasksByDate(date);
    }

    public ListenableFuture<Long> saveTask(Task task) {
        return taskDAO.save(task);
    }
}
