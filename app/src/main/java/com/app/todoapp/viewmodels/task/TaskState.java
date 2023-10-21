package com.app.todoapp.viewmodels.task;

public class TaskState {
    private Object data;
    private TaskStateType filter;

    public TaskState(Object data, TaskStateType filter) {
        this.data = data;
        this.filter = filter;
    }

    public Object getData() {
        return data;
    }

    public TaskStateType getFilter() {
        return filter;
    }
}
