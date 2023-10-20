package com.app.todoapp.database.task;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.app.todoapp.database.categories.Category;

public class TaskWithCategory {
    @Embedded
    public Task task;
    @Relation(parentColumn = "category_id", entityColumn = "uid")
    public Category category;
}
