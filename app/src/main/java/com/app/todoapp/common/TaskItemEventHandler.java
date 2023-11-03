package com.app.todoapp.common;

import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskWithCategory;

import java.util.List;

public interface TaskItemEventHandler {

    void eventHandler(TaskWithCategory item, TaskViewHolder selector);
}
