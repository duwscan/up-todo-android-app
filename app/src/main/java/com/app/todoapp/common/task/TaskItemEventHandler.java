package com.app.todoapp.common.task;

import com.app.todoapp.database.task.TaskWithCategory;

public interface TaskItemEventHandler {

    void eventHandler(TaskWithCategory item, TaskViewHolder selector);
}
