package com.app.todoapp.common.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.app.todoapp.database.DaoInjection;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskDAO;
import com.app.todoapp.database.task.TaskWithCategory;
import com.app.todoapp.state.TaskFilterState;
import com.app.todoapp.state.TaskState;
import com.app.todoapp.state.TaskStateType;
import com.google.common.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskViewModel extends AndroidViewModel {
    private TaskDAO taskDAO;
    private MutableLiveData<TaskState> state = new MutableLiveData<>();

    public LiveData<TaskState> getCurrentState() {
        return state;
    }

    public TaskViewModel(Application application) {
        super(application);
        this.taskDAO = DaoInjection.taskDAO(application);
    }

    public void saveState(TaskState state) {
        this.state.setValue(state);
    }

    public LiveData<List<TaskWithCategory>> getTask() {
        return Transformations.switchMap(state, this::getFilteredTasks);
    }

    private LiveData<List<TaskWithCategory>> getFilteredTasks(TaskState taskState) {
        Map<TaskStateType, TaskState.Filter> currentState = taskState.getState();
        if (currentState.containsKey(TaskStateType.FILTER)) {
            TaskState.Filter filterState = currentState.get(TaskStateType.FILTER);
            assert filterState != null;
            if (filterState.getFilterState().equals(TaskFilterState.DATE)) {
                return taskDAO.getTasksByDate((LocalDate) filterState.getData());
            }
            if (filterState.getFilterState().equals(TaskFilterState.ALL)) {
                taskDAO.getAllTasksWithCategories();
            }
        }
        return taskDAO.getAllTasksWithCategories();
    }

    public LiveData<List<TaskWithCategory>> searchTask(String query) {
        return taskDAO.getTasksByTitle(query);
    }

    public ListenableFuture<Long> saveTask(Task task) {
        return taskDAO.save(task);
    }

    public ListenableFuture<Integer> deleteTask(Task task) {
        return taskDAO.delete(task);
    }
}
