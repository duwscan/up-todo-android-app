package com.app.todoapp.database.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Upsert;

import com.google.common.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TaskDAO {
    @Transaction
    @Query("SELECT * FROM Task t")
    LiveData<List<TaskWithCategory>> getAllTasksWithCategories();

    @Query("SELECT * FROM Task")
    LiveData<List<TaskWithCategory>> getAll();

    @Query("SELECT * FROM Task WHERE dueDate = :filterTime")
    LiveData<List<TaskWithCategory>> getTasksByDate(LocalDate filterTime);

    @Query("SELECT * FROM Task WHERE title LIKE '%' || :key || '%'")
    LiveData<List<TaskWithCategory>> getTasksByTitle(String key);

    @Upsert
    ListenableFuture<Long> save(Task task);

    @Delete
    ListenableFuture<Integer> delete(Task task);
}
