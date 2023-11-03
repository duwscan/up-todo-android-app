package com.app.todoapp.database.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Upsert;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface TaskDAO {
    @Transaction
    @Query("SELECT * FROM Task t")
    LiveData<List<TaskWithCategory>> getAllTasksWithCategories();

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM Task WHERE strftime('%Y-%m-%d', dueDateTime/1000, 'unixepoch') = strftime('%Y-%m-%d', :filterTime/1000, 'unixepoch')")
    LiveData<List<TaskWithCategory>> getTasksByDate(long filterTime);

    @Query("SELECT * FROM Task WHERE title LIKE '%' || :key || '%'")
    LiveData<List<TaskWithCategory>> getTasksByTitle(String key);

    @Upsert
    ListenableFuture<Long> save(Task task);
}
