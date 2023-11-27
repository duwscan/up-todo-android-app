package com.app.todoapp.state;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TaskState {
    public class Filter {
        private TaskFilterState filterState;
        private Object data;

        public Filter(TaskFilterState filterState, Object data) {
            this.filterState = filterState;
            this.data = data;
        }

        public TaskFilterState getFilterState() {
            return filterState;
        }

        public void setFilterState(TaskFilterState filterState) {
            this.filterState = filterState;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    Map<TaskStateType, TaskState.Filter> state = new HashMap<>();

    public void setState(TaskStateType type) {
        state.put(type, null);
    }

    public void setFilterState(TaskFilterState type, Object data) {
        Map<TaskStateType, TaskState.Filter> newState = new HashMap<>();
        if (state != null) {
            newState.putAll(state);
        }
        newState.put(TaskStateType.FILTER, new Filter(type, data));
        this.state = newState;
    }

    public Map<TaskStateType, TaskState.Filter> getState() {
        return state;
    }
}
