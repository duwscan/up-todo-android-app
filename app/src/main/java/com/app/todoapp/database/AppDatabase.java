package com.app.todoapp.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.categories.CategoryDAO;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskDAO;

@Database(entities = {Category.class, Task.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "UpTodo";
    private static volatile AppDatabase INSTANCE;

    // Singleton
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CategoryDAO categoryDAO();

    public abstract TaskDAO taskDAO();
}
