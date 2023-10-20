package com.app.todoapp.database.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Upsert;

import com.app.todoapp.database.categories.Category;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TaskDAO {
    @Transaction
    @Query("SELECT * FROM Task t")
    LiveData<List<TaskWithCategory>> getAllTasksWithCategories();

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM Task WHERE strftime('%Y-%m-%d', dueDateTime/1000, 'unixepoch') = strftime('%Y-%m-%d', :filterTime/1000, 'unixepoch')")
    LiveData<List<TaskWithCategory>> getTasksByDate(long filterTime);

    @Upsert
    ListenableFuture<Long> save(Task task);
}
